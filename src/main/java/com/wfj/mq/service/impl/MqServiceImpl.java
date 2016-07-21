package com.wfj.mq.service.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.dto.ItgMsgHeaderDto;
import com.wfj.mq.dto.MessageDto;
import com.wfj.mq.dto.MessageLogDto;
import com.wfj.mq.entity.*;
import com.wfj.mq.service.ChannelService;
import com.wfj.mq.service.MessageLogService;
import com.wfj.mq.service.MqConfService;
import com.wfj.mq.service.MqService;
import com.wfj.mq.utils.ItgConstent;
import com.wfj.persist.SimpleGenericDAO;
import com.wfj.utils.JsonUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by MaYong on 2015/4/18.
 */
@Service("mqService")
public class MqServiceImpl implements MqService {

    @Resource(name = "mqConfService")
    private MqConfService mqConfService;
    @Resource(name = "channelService")
    private ChannelService channelService;
    private SimpleGenericDAO<InboundConf, Integer> inboundConfDao;
    private SimpleGenericDAO<MessageLog, Long> messageLogDao;
    private SimpleGenericDAO<TxInfo, Long> txInfoDao;
    @Resource(name = "messageLogService")
    private MessageLogService messageLogService;


    public ItgCallbackDto sendMessage(MessageDto messageDto, String uuidMsgString) throws Exception {
        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        properties.setContentEncoding("UTF-8");
        properties.setContentType("text/plain");
        ItgMsgHeaderDto headerDto = messageDto.getHeader();
//        String message = JsonUtils.beanToJson(messageDto);
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
                //direct，关联InboundQueueRef表，取得routeKey，因为不以传入的消息体内的key为准
                List<InboundQueueRef> list = mqConfService.getInboundQueueRef(qry.getSid());
                //先放第一个队列里吧，以后扩展
                InboundQueueRef inboundQueueRef = list.get(0);
                //第二步：发送消息到队列
                Channel channel = channelService.getChannel(groupSid, qry.getSid());
                //add by mayong 2015-08-11
                //特殊转换，取data中字符串发送，一定是点对点的
                ObjectMapper mapper = new ObjectMapper();
                MessageDto srcDto = mapper.readValue(uuidMsgString, MessageDto.class);
                if (srcDto.getHeader().getField3() != null && srcDto.getHeader().getField3().equals("getData")) {
                    uuidMsgString = mapper.writeValueAsString(srcDto.getData());
                }
                //add by mayong
//                channel.basicPublish(exchangeConf.getExchangeName(), inboundQueueRef.getRouteKey(), properties, message.getBytes());
                channel.basicPublish(exchangeConf.getExchangeName(), inboundQueueRef.getRouteKey(), properties, uuidMsgString.getBytes());
                try {
                    TxInfo info = messageLogService.getTxInfo(headerDto.getMessageID(), inboundQueueRef.getQueueConfSid());
                    TxInfo txInfo_up = new TxInfo();
                    txInfo_up.setMessageId(headerDto.getMessageID());
                    txInfo_up.setStatus(1);
                    txInfo_up.setQueueConfSid(inboundQueueRef.getQueueConfSid());
                    txInfoDao.updateByStatementCond("updateStatusByMessageId", txInfo_up);
                    MessageLogDto messageLogDto = new MessageLogDto();
                    messageLogDto.setCode(ItgConstent.TxStep.到达预定队列.getCode());
                    messageLogDto.setValue(ItgConstent.TxStep.到达预定队列.getValue());
                    messageLogService.appendTxInfoLog(info.getSid(), messageLogDto);
                } catch (SQLException e) {
                    throw new Exception("=== 发到队列失败 ===");
                }
                break;
            case 1:
                //fanout
                System.out.println("fanout发送");
                //修改tx_info状态至发送到队列
                try {
                    TxInfo txInfo_up = new TxInfo();
                    txInfo_up.setMessageId(headerDto.getMessageID());
                    txInfo_up.setStatus(1);
                    txInfoDao.updateByStatementCond("updateStatusByMessageId", txInfo_up);
                } catch (SQLException e) {
                    throw new Exception("=== 发到队列失败 ===");
                }
                Channel channelFanout = channelService.getChannel(groupSid, qry.getSid());
                //add by mayong
//                channelFanout.basicPublish(exchangeConf.getExchangeName(), "", properties, message.getBytes());
                channelFanout.basicPublish(exchangeConf.getExchangeName(), "", properties, uuidMsgString.getBytes());
                break;
            case 2:
                //topic,以传入的消息体内的key为准
                List<InboundQueueRef> listTopic = mqConfService.getInboundQueueRef(qry.getSid());
                //先放第一个队列里吧，以后扩展
                InboundQueueRef inboundQueueRefTopic = listTopic.get(0);
                //第二步：发送消息到队列
                Channel channelTopic = channelService.getChannel(groupSid, qry.getSid());
                //add by mayong
//                channel.basicPublish(exchangeConf.getExchangeName(), inboundQueueRef.getRouteKey(), properties, message.getBytes());
                channelTopic.basicPublish(exchangeConf.getExchangeName(), inboundQueueRefTopic.getRouteKey(), properties, uuidMsgString.getBytes());
                try {
                    TxInfo info = messageLogService.getTxInfo(headerDto.getMessageID(), -9999);
                    TxInfo txInfo_up = new TxInfo();
                    txInfo_up.setMessageId(headerDto.getMessageID());
                    txInfo_up.setStatus(1);
                    txInfo_up.setQueueConfSid(-9999);
                    txInfoDao.updateByStatementCond("updateStatusByMessageId", txInfo_up);
                    MessageLogDto messageLogDto = new MessageLogDto();
                    messageLogDto.setCode(ItgConstent.TxStep.到达预定队列.getCode());
                    messageLogDto.setValue(ItgConstent.TxStep.到达预定队列.getValue());
                    messageLogService.appendTxInfoLog(info.getSid(), messageLogDto);
                } catch (SQLException e) {
                    throw new Exception("=== 发到队列失败 ===");
                }
                break;
        }
        return null;
    }

    @Autowired
    public void setDpClient(SqlMapClient dpClient) {
        this.inboundConfDao = new SimpleGenericDAO<InboundConf, Integer>(dpClient, InboundConf.class);
        this.messageLogDao = new SimpleGenericDAO<MessageLog, Long>(dpClient, MessageLog.class);
        this.txInfoDao = new SimpleGenericDAO<TxInfo, Long>(dpClient, TxInfo.class);
    }

}
