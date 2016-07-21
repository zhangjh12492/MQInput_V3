package com.wfj.mq.entity;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 2015-04-17
 * Time: 17:43:44
 * To change this template use File | Settings | File Templates.
 */

public class ServerConf {
    private Integer serverPort;  //
    private String userName;  //
    private String serverIp;  //
    private Integer groupSid;  //
    private Integer sid;  //
    private String password;  //

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getGroupSid() {
        return groupSid;
    }

    public void setGroupSid(Integer groupSid) {
        this.groupSid = groupSid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}