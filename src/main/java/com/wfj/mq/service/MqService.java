package com.wfj.mq.service;

import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.dto.MessageDto;

/**
 * Created by MaYong on 2015/4/18.
 */
public interface MqService {

    public ItgCallbackDto sendMessage(MessageDto messageDto, String uuidMsgString) throws Exception;
}
