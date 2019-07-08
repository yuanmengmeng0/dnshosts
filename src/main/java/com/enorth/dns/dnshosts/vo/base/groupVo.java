package com.enorth.dns.dnshosts.vo.base;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/17
 * */

import java.util.Date;

public class groupVo {
    private int groupId;
    private String groupName;
    private int state;
    private int listOrder;
    private String modUserId;
    private String modUserName;
    private Date modDate = new Date();

    public groupVo(int groupId, String groupName, int state, int listOrder, String modUserId, String modUserName, Date modDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.state = state;
        this.listOrder = listOrder;
        this.modUserId = modUserId;
        this.modUserName = modUserName;
        this.modDate = modDate;
    }

    public groupVo() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getListOrder() {
        return listOrder;
    }

    public void setListOrder(int listOrder) {
        this.listOrder = listOrder;
    }

    public String getModUserId() {
        return modUserId;
    }

    public void setModUserId(String modUserId) {
        this.modUserId = modUserId;
    }

    public String getModUserName() {
        return modUserName;
    }

    public void setModUserName(String modUserName) {
        this.modUserName = modUserName;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }
}
