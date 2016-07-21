package com.wfj.mq.dto;

import java.io.Serializable;

/**
 * Created by MaYong on 2014/12/16.
 */
public class HessianDto implements Serializable{
    private String bizData;

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }
}
