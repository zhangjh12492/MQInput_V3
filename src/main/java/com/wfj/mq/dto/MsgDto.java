package com.wfj.mq.dto;

/**
 * Created by MaYong on 2014/6/24.
 */
public class MsgDto {
    private Boolean access;

    private String msg;

    public MsgDto() {

    }

    public MsgDto(Boolean access, String msg) {
        this.access = access;
        this.msg = msg;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
