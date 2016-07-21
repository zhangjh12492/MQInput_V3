package com.wfj.mq.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 2015-04-19
 * Time: 12:32:45
 * To change this template use File | Settings | File Templates.
 */

public class TxInfo implements Serializable{
    private Date inTime;  //
    private String messageId;  //
    private Integer status;  //
    private Date outTime;  //
    private Integer queueConfSid;  //
    private Long sid;  //
    private String systemNo;
    private String serviceNo;
    private Integer retryTimes;
    private Integer exchangeType;

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Integer getQueueConfSid() {
        return queueConfSid;
    }

    public void setQueueConfSid(Integer queueConfSid) {
        this.queueConfSid = queueConfSid;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

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

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }
}