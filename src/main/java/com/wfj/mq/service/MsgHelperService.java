package com.wfj.mq.service;

import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.dto.MessageDto;

import java.sql.SQLException;

/**
 * Created by MaYong on 2014/12/17.
 */
public interface MsgHelperService {

    public MessageDto parserMessage(String msg);

    public ItgCallbackDto parserCallBackMessage(String msg);

    public String validatorMsg(MessageDto messageDto, String message) throws SQLException, Exception;

    public Integer authMsg(MessageDto messageDto) throws SQLException;

    public MessageDto createMsgUUID(MessageDto messageDto);
}
