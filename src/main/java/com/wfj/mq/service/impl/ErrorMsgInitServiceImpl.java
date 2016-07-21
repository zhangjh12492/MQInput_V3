package com.wfj.mq.service.impl;

import com.wfj.exception.handler.EcMapperHandler;
import com.wfj.exception.util.PropertiesLoad;
import com.wfj.exception.util.SystemBootstrap;
import com.wfj.mq.service.ErrorMsgInitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MaYong on 2015/10/8.
 */
@Service("errorMsgInitService")
public class ErrorMsgInitServiceImpl implements ErrorMsgInitService {

    private final Logger logger = LoggerFactory.getLogger(ErrorMsgInitServiceImpl.class);

    @Value("#{configProperties['sysCode']}")
    private String sysCode;

    @Value("#{configProperties['messageUrl']}")
    private String messageUrl;

    @Value("#{configProperties['destUrl']}")
    private String destUrl;

    @Override
    public void init() {
        Map<String, String> bussMap = new HashMap<String, String>();
        Map<String, String> errMap = new HashMap<String, String>();
        bussMap.put("001", "ceshi_订单拆分");
        bussMap.put("002", "ceshi_订单支付");
        bussMap.put("003", "ceshi_订单库存");
        errMap.put("001", "ceshi_商品信息不全");
        errMap.put("002", "ceshi_物流地址不符合要求");
        errMap.put("003", "ceshi_库存有误");
        EcMapperHandler.getInstanceEcMap().init(bussMap, errMap);
        SystemBootstrap.afterPropertiesSet();
        PropertiesLoad.putProperties("mq_server", messageUrl);
        PropertiesLoad.putProperties("mq_dest_url", destUrl);
        PropertiesLoad.putProperties("sysCode", sysCode);
        logger.debug("=== 异常信息初始化完成 ===");
    }
}
