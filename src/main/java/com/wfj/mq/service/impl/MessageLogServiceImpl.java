package com.wfj.mq.service.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.rabbitmq.client.Channel;
import com.wfj.mq.dto.ItgMsgHeaderDto;
import com.wfj.mq.dto.MessageDto;
import com.wfj.mq.dto.MessageLogDto;
import com.wfj.mq.entity.*;
import com.wfj.mq.service.MessageLogService;
import com.wfj.mq.service.MqConfService;
import com.wfj.mq.utils.ItgConstent;
import com.wfj.persist.SimpleGenericDAO;
import com.wfj.utils.DateUtils;
import com.wfj.utils.JsonUtils;
import com.wfj.utils.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by MaYong on 2015/4/19.
 */
@Service("messageLogService")
public class MessageLogServiceImpl implements MessageLogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "mqConfService")
    private MqConfService mqConfService;
    private SimpleGenericDAO<InboundConf, Integer> inboundConfDao;
    private SimpleGenericDAO<MessageLog, Long> messageLogDao;
    private SimpleGenericDAO<TxInfo, Long> txInfoDao;
    private SimpleGenericDAO<TxInfoLog, Long> txInfoLogDao;
    @Resource(name = "redisCache")
    private Cache cache;

    public void setTxInfo(TxInfo txInfo) {
        String key = "getTxInfo:" + txInfo.getMessageId() + "==" + txInfo.getQueueConfSid();
        try {
            cache.setObject(key, txInfo, 300);
        } catch (Exception e) {
            logger.debug("redis error:" + e.getMessage());
        }
    }

    public TxInfo getTxInfo(String messageId, Integer queueConfSid) throws SQLException {
        String key = "getTxInfo:" + messageId + "==" + queueConfSid;
        TxInfo txInfo = null;
        try {
            txInfo = (TxInfo) cache.getObject(key);
        } catch (Exception e) {
            logger.debug("redis error:" + e.getMessage());
        }
        if (txInfo == null) {
            TxInfo qryInfo = new TxInfo();
            qryInfo.setMessageId(messageId);
            qryInfo.setQueueConfSid(queueConfSid);
            txInfo = txInfoDao.findByCond(qryInfo);
            try {
                cache.setObject(key, txInfo, 300);
            } catch (Exception e) {
                logger.debug("redis error:" + e.getMessage());
            }
        }
        return txInfo;
    }


    public void appendTxInfoLog(Long txInfoSid, MessageLogDto dto) throws SQLException {
        TxInfoLog txInfoLog = new TxInfoLog();
        txInfoLog.setSid(txInfoSid);
        dto.setOptTime(DateUtils.formatDateTime(new Date()));
        txInfoLog.setContent("," + JsonUtils.beanToJson(dto));
        txInfoLogDao.updateByStatementCond("appendById", txInfoLog);
    }

    @Transactional(rollbackFor = SQLException.class)
    public void saveMessageAndTxinfo(MessageDto messageDto, String uuidMsgString) throws SQLException {
        String message = JsonUtils.beanToJson(messageDto);
        ItgMsgHeaderDto headerDto = messageDto.getHeader();
        //1、查接入信息
/*
        InboundConf conf = new InboundConf();
        conf.setSystemNo(headerDto.getSourceSysID());
        conf.setServiceNo(headerDto.getServiceID());
        InboundConf qry = inboundConfDao.findByCond(conf);
*/
        InboundConf qry = mqConfService.getInboundConf(headerDto);
        Integer groupSid = qry.getGroupSid();
        Integer exchangeType = qry.getExchangeType();
        ExchangeConf exchangeConf = mqConfService.getExchangeConf(qry.getExchangeConfSid());
        switch (exchangeType) {
            case 0:
                //点对点，只需要存储一条
                //direct，关联InboundQueueRef表，取得routeKey，因为不以传入的消息体内的key为准
                List<InboundQueueRef> list = mqConfService.getInboundQueueRef(qry.getSid());
                //先放第一个队列里吧，以后扩展
                InboundQueueRef inboundQueueRef = list.get(0);
                //第一步：存储报文
                TxInfo txInfo = new TxInfo();
                txInfo.setMessageId(headerDto.getMessageID());
                txInfo.setStatus(ItgConstent.TxInfoStatus.初始存储.getCode());
                txInfo.setSystemNo(headerDto.getSourceSysID());
                txInfo.setServiceNo(headerDto.getServiceID());
                txInfo.setQueueConfSid(inboundQueueRef.getQueueConfSid());
                txInfo.setInTime(new Date());
                txInfo.setRetryTimes(0);
                txInfo.setExchangeType(ItgConstent.ExchangeType.direct.getCode());
                Long txInfoSid = txInfoDao.insert(txInfo);
                MessageLog messageLog = new MessageLog();
                //add by mayong
//                messageLog.setContent(message);
                messageLog.setContent(uuidMsgString);
                messageLog.setCreateTime(new Date());
                messageLog.setMessageId(headerDto.getMessageID());
                messageLog.setMessageType(0);  //消息原文
                messageLog.setTxInfoSid(txInfoSid);
                messageLogDao.insert(messageLog);
                //记录TxInfoLog
                TxInfoLog log = new TxInfoLog();
                log.setSid(txInfoSid);
                MessageLogDto logDto = new MessageLogDto();
                logDto.setCode(ItgConstent.TxStep.消息存储.getCode());
                logDto.setValue(ItgConstent.TxStep.消息存储.getValue());
                logDto.setOptTime(DateUtils.formatDateTime(new Date()));
                log.setContent(JsonUtils.beanToJson(logDto));
                log.setMessageId(headerDto.getMessageID());
                txInfoLogDao.insert(log);
                //存储至redis缓存
                txInfo.setSid(txInfoSid);
                setTxInfo(txInfo);
//                //第二步：发送消息到队列
//                Channel channel = channelService.getChannel(groupSid, qry.getSid());
//                channel.basicPublish(exchangeConf.getExchangeName(), inboundQueueRef.getRouteKey(), properties, message.getBytes());
                break;
            case 2:
                //广播方式(topic)，存一条，队列设置 -9999
                //topic
                List<InboundQueueRef> listTopic = mqConfService.getInboundQueueRef(qry.getSid());
                //循环存储
//                InboundQueueRef inboundQueueRefTopic = list.get(0);
                InboundQueueRef topicRef = listTopic.get(0);
                //第一步：存储报文
                TxInfo txInfoTopic = new TxInfo();
                txInfoTopic.setMessageId(headerDto.getMessageID());
                txInfoTopic.setStatus(ItgConstent.TxInfoStatus.初始存储.getCode());
                txInfoTopic.setSystemNo(headerDto.getSourceSysID());
                txInfoTopic.setServiceNo(headerDto.getServiceID());
                txInfoTopic.setQueueConfSid(-9999);
                txInfoTopic.setRetryTimes(0);
                txInfoTopic.setExchangeType(ItgConstent.ExchangeType.topic.getCode());
                txInfoTopic.setInTime(new Date());
                Long txInfoSidTopic = txInfoDao.insert(txInfoTopic);
                MessageLog messageLogTopic = new MessageLog();
                messageLogTopic.setContent(message);
                messageLogTopic.setMessageId(headerDto.getMessageID());
                messageLogTopic.setMessageType(0);  //消息原文
                messageLogTopic.setTxInfoSid(txInfoSidTopic);
                messageLogDao.insert(messageLogTopic);
                TxInfoLog logTopic = new TxInfoLog();
                logTopic.setSid(txInfoSidTopic);
                MessageLogDto logDtoTopic = new MessageLogDto();
                logDtoTopic.setCode(ItgConstent.TxStep.消息存储.getCode());
                logDtoTopic.setValue(ItgConstent.TxStep.消息存储.getValue());
                logDtoTopic.setOptTime(DateUtils.formatDateTime(new Date()));
                logTopic.setContent(JsonUtils.beanToJson(logDtoTopic));
                logTopic.setMessageId(headerDto.getMessageID());
                logTopic.setQueueConfSid(-9999);
                txInfoLogDao.insert(logTopic);
                break;
        }

    }

    @Autowired
    public void setDpClient(SqlMapClient dpClient) {
        this.inboundConfDao = new SimpleGenericDAO<InboundConf, Integer>(dpClient, InboundConf.class);
        this.messageLogDao = new SimpleGenericDAO<MessageLog, Long>(dpClient, MessageLog.class);
        this.txInfoDao = new SimpleGenericDAO<TxInfo, Long>(dpClient, TxInfo.class);
        this.txInfoLogDao = new SimpleGenericDAO<TxInfoLog, Long>(dpClient, TxInfoLog.class);
    }

}
