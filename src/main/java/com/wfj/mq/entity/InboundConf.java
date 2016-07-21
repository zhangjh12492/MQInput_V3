package com.wfj.mq.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 2015-04-17
 * Time: 17:43:43
 * To change this template use File | Settings | File Templates.
 */

public class InboundConf implements Serializable {
    private String systemNo;  //
    private String serviceNo;  //
    private String routeKey;  //
    private Integer groupSid;  //
    private String inboundDesc;  //
    private Integer exchangeType;  //
    private Integer sid;  //
    private Integer maxLength;  //
    private Integer exchangeConfSid; //

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public Integer getGroupSid() {
        return groupSid;
    }

    public void setGroupSid(Integer groupSid) {
        this.groupSid = groupSid;
    }


    public String getInboundDesc() {
        return inboundDesc;
    }

    public void setInboundDesc(String inboundDesc) {
        this.inboundDesc = inboundDesc;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getExchangeConfSid() {
        return exchangeConfSid;
    }

    public void setExchangeConfSid(Integer exchangeConfSid) {
        this.exchangeConfSid = exchangeConfSid;
    }
}