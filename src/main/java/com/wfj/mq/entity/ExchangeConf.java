package com.wfj.mq.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 2015-04-18
 * Time: 21:49:05
 * To change this template use File | Settings | File Templates.
 */

public class ExchangeConf implements Serializable{
    private Integer status;  //
    private String exchangeDesc;  //
    private Integer exchangeType;  //
    private String exchangeName;  //
    private Integer sid;  //

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExchangeDesc() {
        return exchangeDesc;
    }

    public void setExchangeDesc(String exchangeDesc) {
        this.exchangeDesc = exchangeDesc;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

}