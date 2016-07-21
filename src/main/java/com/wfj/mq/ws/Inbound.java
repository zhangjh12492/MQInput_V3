package com.wfj.mq.ws;

/**
 * Created by MaYong on 2015/8/3.
 */
public interface Inbound {
    public String dataSyncJson(String message);

    public String dataSyncXML(String message);

    public String dataSyncJsonTest(String message);

    public String dataSyncXMLTest(String message);
}
