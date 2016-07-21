package com.wfj.mq.dto;

/**
 * Created by suifeng on 15-4-23.
 */
public class MessageLogDto {
    private String code;
    private String value;
    private String optTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }
}
