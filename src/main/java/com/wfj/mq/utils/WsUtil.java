package com.wfj.mq.utils;

/*
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
*/

/**
 * Created by MaYong on 2015/7/30.
 */
public class WsUtil {

/*
    private final static Logger logger = LoggerFactory.getLogger(WsUtil.class);

    public static String ws_post(String wsdlUrl, String message) {
        Client client = getClient(wsdlUrl);
        Object[] objects = null;
        try {
            objects = client.invoke("dataSyncJson", message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return objects[0].toString();
    }


    public static Client getClient(String url) {
        //动态调用的客户端工厂类
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        final Client client = factory.createClient(url);
        //设置超时单位为毫秒
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(3000);  //连接超时
        httpClientPolicy.setAllowChunking(false);    //取消块编码
        httpClientPolicy.setReceiveTimeout(3000);     //响应超时
        http.setClient(httpClientPolicy);
        return client;
    }

    public static void main(String[] args) throws Exception {
        String wsdlUrl = "http://172.16.11.11:8080/webws/CalculatorPort?wsdl";
        //动态调用的客户端工厂类
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        final Client client = factory.createClient(wsdlUrl);

        //设置超时单位为毫秒
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();

        httpClientPolicy.setConnectionTimeout(3000);  //连接超时
        httpClientPolicy.setAllowChunking(false);    //取消块编码
        httpClientPolicy.setReceiveTimeout(3000);     //响应超时
        http.setClient(httpClientPolicy);

    }
*/


}
