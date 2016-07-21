package com.wfj.mq.service;

import com.wfj.mq.dto.ItgMsgHeaderDto;
import com.wfj.mq.dto.MessageDto;
import com.wfj.mq.entity.ExchangeConf;
import com.wfj.mq.entity.InboundConf;
import com.wfj.mq.entity.InboundQueueRef;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by MaYong on 2015/4/18.
 */
public interface MqConfService {
    public List<InboundQueueRef> getInboundQueueRef(Integer inboundConfSid) throws SQLException;

    public ExchangeConf getExchangeConf(Integer sid) throws SQLException;

    public InboundConf getInboundConf(MessageDto messageDto) throws SQLException;

    public InboundConf getInboundConf(ItgMsgHeaderDto headerDto) throws SQLException;

    public Integer getExchangeType(MessageDto messageDto) throws SQLException;
}
