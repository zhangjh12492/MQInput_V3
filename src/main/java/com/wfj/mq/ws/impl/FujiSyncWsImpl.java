package com.wfj.mq.ws.impl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.wfj.mq.dto.MsgDto;
import com.wfj.mq.service.ChannelService;
import com.wfj.ws.FujiSyncWs;
import com.wfj.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by MaYong on 2014/9/1.
 */
//@WebService(endpointInterface = "com.wfj.ws.FujiSyncWs")
//@WebService(targetNamespace = "http://ws.wangfujing.com")
public class FujiSyncWsImpl implements FujiSyncWs {

    private final Logger logger = LoggerFactory.getLogger(FujiSyncWsImpl.class);

    @Resource(name = "channelService")
    private ChannelService channelService;

/*
    @Value("#{propertyConfig[EfutureERP_SUPPLIER_EXCHANGE]}")
    private String SUPPLIERExchangeName;
    @Value("#{propertyConfig[EfutureERP_MANAGECATEGORY_EXCHANGE]}")
    private String MANAGECATEGORYExchangeName;
    @Value("#{propertyConfig[EfutureERP_PRODUCT_EXCHANGE]}")
    private String PRODUCTExchangeName;
    @Value("#{propertyConfig[EfutureERP_PRICE_EXCHANGE]}")
    private String PRICEExchangeName;
    @Value("#{propertyConfig[EfutureERP_SUPPLIERBARCODE_EXCHANGE]}")
    private String SUPPLIERBARCODEExchangeName;
    @Value("#{propertyConfig[EfutureERP_ORDERS_EXCHANGE]}")
    private String ORDERSExchangeName;
    @Value("#{propertyConfig[EfutureERP_AUTHORIZATIONCARD_EXCHANGE]}")
    private String AUTHORIZATIONCARDExchangeName;
    @Value("#{propertyConfig[EFUTUREERP_PAYMODELIMIT_EXCHANGE]}")
    private String PAYMODELIMITExchangeName;
    @Value("#{propertyConfig[EFUTUREERP_ERPCHANGE_EXCHANGE]}")
    private String erpChangeExchangeName;
    @Value("#{propertyConfig[EFUTUREERP_CONSUMER_EXCHANGE]}")
    private String consumerExchangeName;
    @Value("#{propertyConfig[EFUTUREERP_SALEAMOUNT_EXCHANGE]}")
    private String saleAmountExchangeName;
    @Value("#{propertyConfig[EFUTUREERP_PRODUCTMULTISUPPLIERS_EXCHANGE]}")
    private String productMultiSuppliersExchangeName;
    @Value("#{propertyConfig[EFUTUREERP_RETURNORDERS_EXCHANGE]}")
    private String returnOrdersExchangeName;
    @Value("#{propertyConfig[EFUTUREERP_COMBOCODE_EXCHANGE]}")
    private String comboCodeExchangeName;

    @Resource(name = "EfutureERPSupplierTemplate")
    private AmqpTemplate supplierTemplate;
    @Resource(name = "EfutureERPManageCategoryTemplate")
    private AmqpTemplate manageCategoryTemplate;
    @Resource(name = "EfutureERPProductTemplate")
    private AmqpTemplate productTemplate;
    @Resource(name = "EfutureERPPriceTemplate")
    private AmqpTemplate priceTemplate;
    @Resource(name = "EfutureERPSupplierBarCodeTemplate")
    private AmqpTemplate supplierBarcodeTemplate;
    @Resource(name = "EfutureERPOrdersTemplate")
    private AmqpTemplate ordersTemplate;
    @Resource(name = "EfutureERPAuthorizationcardTemplate")
    private AmqpTemplate authorizationcardTemplate;
    @Resource(name = "EfutureERPPayModeLimitTemplate")
    private AmqpTemplate payModeLimitTemplate;
    @Resource(name = "EfutureERPChangeTemplate")
    private AmqpTemplate erpChangeTemplate;
    @Resource(name = "efutureCRMConsumerTemplate")
    private AmqpTemplate consumerTemplate;
    @Resource(name = "efutureERPSaleAmountTemplate")
    private AmqpTemplate saleAmountTemplate;
    @Resource(name = "productMultiSuppliersTemplate")
    private AmqpTemplate productMultiSuppliersTemplate;
    @Resource(name = "returnOrdersTemplate")
    private AmqpTemplate returnOrdersTemplate;
    @Resource(name = "comboCodeTemplate")
    private AmqpTemplate comboCodeTemplate;
*/

    @Override
    public String fujiSync(String queueName, String queueData) {

        logger.info("Listen Begin:" + "\nExchangeName:" + queueName
                + "\nQueueData:" + queueData);
        MsgDto msgDto = new MsgDto();
        if (queueData.trim().equals("") || queueData == null) {
            logger.error("数据不能为空");
            msgDto.setMsg("数据不能为空");
            msgDto.setAccess(false);
            logger.info("Listen End");
            return XmlUtils.beanToXml(msgDto);
        }
        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        properties.setContentEncoding("UTF-8");
        properties.setContentType("text/plain");
        Channel channel = null;
        try {
            channel = channelService.getChannel(1, -1);
            channel.basicPublish(queueName.trim(), null, properties, queueData.getBytes());
            msgDto.setAccess(true);
        } catch (IOException e) {
            msgDto.setMsg("没有找到 ExchangeName" + e.getMessage());
            msgDto.setAccess(false);
            e.printStackTrace();
        } catch (Exception e) {
            msgDto.setMsg("获取连接异常" + e.getMessage());
            msgDto.setAccess(false);
            e.printStackTrace();
        }
        return XmlUtils.beanToXml(msgDto);
    }

}
