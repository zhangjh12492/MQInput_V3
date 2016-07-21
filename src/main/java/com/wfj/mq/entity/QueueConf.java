package com.wfj.mq.entity;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 2015-04-17
 * Time: 17:43:44
 * To change this template use File | Settings | File Templates.
 */

public class QueueConf {
    private Integer status;  //
    private String queueName;  //
    private Integer outboundConfSid;  //
    private String queueDesc;  //
    private Integer sid;  //

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Integer getOutboundConfSid() {
        return outboundConfSid;
    }

    public void setOutboundConfSid(Integer outboundConfSid) {
        this.outboundConfSid = outboundConfSid;
    }

    public String getQueueDesc() {
        return queueDesc;
    }

    public void setQueueDesc(String queueDesc) {
        this.queueDesc = queueDesc;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

}