package com.wfj.mq.ws.impl;

import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.service.ItgInputService;
import com.wfj.mq.ws.Inbound;
import com.wfj.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by MaYong on 2015/8/3.
 */

public class InboundImpl implements Inbound {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "itgInputService")
    private ItgInputService itgInputService;

    /**
     * 数据用soap传输，但内容还是标准的json格式
     *
     * @param message
     * @return
     */
    @Override
    public String dataSyncJson(String message) {
        ItgCallbackDto itgCallbackDto = null;
        try {
            itgCallbackDto = itgInputService.input(message);
        } catch (Exception e) {
            logger.error("=== 调用接入服务出错" + e.getMessage() + " ===");
            itgCallbackDto = new ItgCallbackDto();
        }
        String str = JsonUtils.beanToJson(itgCallbackDto);
        return str;
    }

    /**
     * 数据用标准的XML文档传输，包括<header/>与<data/>段
     *
     * @param message
     * @return
     */
    @Override
    public String dataSyncXML(String message) {
        return "";
    }

    @Override
    public String dataSyncJsonTest(String message) {
        ItgCallbackDto itgCallbackDto = null;
        try {
            itgCallbackDto = itgInputService.input(message);
        } catch (Exception e) {
            logger.error("=== 调用接入服务出错" + e.getMessage() + " ===");
            itgCallbackDto = new ItgCallbackDto();
        }
        String str = JsonUtils.beanToJson(itgCallbackDto);
        return str;
    }

    @Override
    public String dataSyncXMLTest(String message) {
        return "";
    }

}
