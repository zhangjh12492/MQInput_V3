package com.wfj.mq.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 2015-04-17
 * Time: 17:43:45
 * To change this template use File | Settings | File Templates.
 */

public class InboundQueueRef implements Serializable {
    private String routeKey;  //
    private String memo;  //
    private Integer inboundConfSid;  //
    private Integer queueConfSid;  //
    private Integer sid;  //

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getmemo() {
        return memo;
    }

    public void setmemo(String memo) {
        this.memo = memo;
    }

    public Integer getInboundConfSid() {
        return inboundConfSid;
    }

    public void setInboundConfSid(Integer inboundConfSid) {
        this.inboundConfSid = inboundConfSid;
    }

    public Integer getQueueConfSid() {
        return queueConfSid;
    }

    public void setQueueConfSid(Integer queueConfSid) {
        this.queueConfSid = queueConfSid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

}