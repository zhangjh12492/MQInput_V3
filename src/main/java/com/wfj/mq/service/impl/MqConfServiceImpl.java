package com.wfj.mq.service.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wfj.mq.dto.ItgMsgHeaderDto;
import com.wfj.mq.dto.MessageDto;
import com.wfj.mq.entity.ExchangeConf;
import com.wfj.mq.entity.InboundConf;
import com.wfj.mq.entity.InboundQueueRef;
import com.wfj.mq.service.MqConfService;
import com.wfj.persist.SimpleGenericDAO;
import com.wfj.utils.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by MaYong on 2015/4/18.
 */
@Service("mqConfService")
public class MqConfServiceImpl implements MqConfService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private SimpleGenericDAO<InboundConf, Integer> inboundConfDao;
    private SimpleGenericDAO<ExchangeConf, Integer> exchangeConfDao;
    private SimpleGenericDAO<InboundQueueRef, Integer> inboundQueueRefDao;
    @Resource(name = "redisCache")
    private Cache cache;

    public List<InboundQueueRef> getInboundQueueRef(Integer inboundConfSid) throws SQLException {
        String key = "getInboundQueueRef:" + inboundConfSid;
        List<InboundQueueRef> queueRefs = null;
        try {
            queueRefs = (List<InboundQueueRef>) cache.getObject(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (queueRefs == null) {
            InboundQueueRef ref = new InboundQueueRef();
            ref.setInboundConfSid(inboundConfSid);
            queueRefs = inboundQueueRefDao.findListByCond(ref);
            try {
                cache.setObject(key, queueRefs, 3000);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return queueRefs;
    }

    public ExchangeConf getExchangeConf(Integer sid) throws SQLException {
        String key = "getExchangeConf:" + sid;
        ExchangeConf exchangeConf = null;
        try {
            exchangeConf = (ExchangeConf) cache.getObject(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (exchangeConf == null) {
            exchangeConf = exchangeConfDao.findById(sid);
            try {
                cache.setObject(key, exchangeConf, 3000);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return exchangeConf;
    }

    public InboundConf getInboundConf(MessageDto messageDto) throws SQLException {
        ItgMsgHeaderDto headerDto = messageDto.getHeader();
        return getInboundConf(headerDto);
    }

    public InboundConf getInboundConf(ItgMsgHeaderDto headerDto) throws SQLException {
        String key = "getInboundConf:" + headerDto.getSourceSysID() + "===" + headerDto.getServiceID();
        InboundConf qry = null;
        try {
            qry = (InboundConf) cache.getObject(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (qry == null) {
            InboundConf conf = new InboundConf();
            conf.setSystemNo(headerDto.getSourceSysID());
            conf.setServiceNo(headerDto.getServiceID());
            qry = inboundConfDao.findByCond(conf);
            try {
                cache.setObject(key, qry, 3000);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return qry;
    }

    public Integer getExchangeType(MessageDto messageDto) throws SQLException {
        InboundConf conf = new InboundConf();
        conf.setSystemNo(messageDto.getHeader().getSourceSysID());
        conf.setServiceNo(messageDto.getHeader().getServiceID());
        InboundConf qry = inboundConfDao.findByCond(conf);
        return qry.getExchangeType();
    }

    @Autowired
    public void setDpClient(SqlMapClient dpClient) {
        this.inboundConfDao = new SimpleGenericDAO<InboundConf, Integer>(dpClient, InboundConf.class);
        this.exchangeConfDao = new SimpleGenericDAO<ExchangeConf, Integer>(dpClient, ExchangeConf.class);
        this.inboundQueueRefDao = new SimpleGenericDAO<InboundQueueRef, Integer>(dpClient, InboundQueueRef.class);
    }

}
