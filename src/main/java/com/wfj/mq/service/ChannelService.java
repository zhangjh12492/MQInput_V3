package com.wfj.mq.service;

import com.rabbitmq.client.Channel;

/**
 * Created by MaYong on 2015/4/18.
 */
public interface ChannelService {


    public Channel getChannel(Integer groupSid, Integer inboundSid) throws Exception;
}
