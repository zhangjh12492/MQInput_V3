package com.wfj.ws;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by MaYong on 2014/9/1.
 */
@WebService(name = "FujiSyncWs", targetNamespace = "http://ws.wfj.com/")
public interface FujiSyncWs {

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "fujiSync", targetNamespace = "http://ws.wfj.com/", className = "axis2.FujiSync")
    @ResponseWrapper(localName = "fujiSyncResponse", targetNamespace = "http://ws.wfj.com/",
            className = "com.wfj.ws.FujiSyncResponse")
    public String fujiSync(
            @WebParam(name = "queueName", targetNamespace = "") String queueName,
            @WebParam(name = "queueData", targetNamespace = "") String queueData);


}
