package com.enorth.dns.dnshosts.vo.base;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/17
 * */

import java.util.Date;

public class hostsVo {
    private int hostsId;
    private int groupId;
    private int ipVersion =4;
    private String ipAddress;
    private String hostNames;
    private String memo;
    private int state;
    private int listOrder;
    private String modUserId;
    private String modUserName;
    private Date modDate = new Date();

    public hostsVo() {
    }

    public hostsVo(int hostsId, int groupId, int ipVersion, String ipAddress, String hostNames, String memo, int state, int listOrder, String modUserId, String modUserName, Date modDate) {
        this.hostsId = hostsId;
        this.groupId = groupId;
        this.ipVersion = ipVersion;
        this.ipAddress = ipAddress;
        this.hostNames = hostNames;
        this.memo = memo;
        this.state = state;
        this.listOrder = listOrder;
        this.modUserId = modUserId;
        this.modUserName = modUserName;
        this.modDate = modDate;
    }

    public int getHostsId() {
        return hostsId;
    }

    public void setHostsId(int hostsId) {
        this.hostsId = hostsId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(int ipVersion) {
        this.ipVersion = ipVersion;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostNames() {
        return hostNames;
    }

    public void setHostNames(String hostNames) {
        this.hostNames = hostNames;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
