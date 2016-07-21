package com.wfj.mq.entity;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 2015-04-18
 * Time: 10:51:57
 * To change this template use File | Settings | File Templates.
 */

public class GroupConf {
    private String userName;  //
    private String groupDesc;  //
    private Integer sid;  //
    private String password;  //
    private String groupName;  //

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}