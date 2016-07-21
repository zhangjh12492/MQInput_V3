package com.wfj.mq.dto;

/**
 * Created by MaYong on 2014/12/5.
 * 报文消息
 */
public class MessageDto {
    private ItgMsgHeaderDto header;
    private Object data;

    public ItgMsgHeaderDto getHeader() {
        return header;
    }

    public void setHeader(ItgMsgHeaderDto header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
