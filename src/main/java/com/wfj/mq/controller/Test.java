package com.wfj.mq.controller;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQContentHeader;
import com.rabbitmq.client.impl.ContentHeaderPropertyWriter;
import com.wfj.mq.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by MaYong on 2015/4/17.
 */
@Controller
@RequestMapping("/test")
public class Test {

    @Resource(name = "channelService")
//    @Aut
// owired
    private ChannelService channelService;

    @ResponseBody
    @RequestMapping(params = "input")
    public String input() {
        String msg = "1234567890";
        try {
            Channel channel = channelService.getChannel(1, 1);
            AMQP.BasicProperties properties = new AMQP.BasicProperties();
            properties.setContentEncoding("UTF-8");
            properties.setContentType("text/plain");
            channel.basicPublish("directExchange", "directRouteKey", properties, msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "xxx";
    }


}
