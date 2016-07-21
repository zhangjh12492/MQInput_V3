package com.wfj.mq.dto;

/**
 * Created by MaYong on 2014/12/5.
 * 报文消息
 */
public class MessageDtoByDestCallType {
    private ItgMsgHeaderDtoByDestCallType header;
    private Object data;

    public ItgMsgHeaderDtoByDestCallType getHeader() {
        return header;
    }

    public void setHeader(ItgMsgHeaderDtoByDestCallType header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
