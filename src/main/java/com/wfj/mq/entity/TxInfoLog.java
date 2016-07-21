package com.wfj.mq.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 2015-04-19
 * Time: 12:32:45
 * To change this template use File | Settings | File Templates.
 */

public class TxInfoLog {
    private String content;  //
    private Long sid;  //
    private String messageId;
    private Integer queueConfSid;


    public Integer getQueueConfSid() {
        return queueConfSid;
    }

    public void setQueueConfSid(Integer queueConfSid) {
        this.queueConfSid = queueConfSid;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

}