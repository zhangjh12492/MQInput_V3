package com.wfj.mq.service;

import com.wfj.mq.dto.MessageDto;
import com.wfj.mq.dto.MessageLogDto;
import com.wfj.mq.entity.TxInfo;

import java.sql.SQLException;

/**
 * Created by MaYong on 2015/4/19.
 */
public interface MessageLogService {
    public void setTxInfo(TxInfo txInfo);

    public TxInfo getTxInfo(String messageId, Integer queueConfSid) throws SQLException;

    public void appendTxInfoLog(Long txInfoSid, MessageLogDto dto) throws SQLException;

    public void saveMessageAndTxinfo(MessageDto messageDto, String uuidMsgString) throws SQLException;
}
