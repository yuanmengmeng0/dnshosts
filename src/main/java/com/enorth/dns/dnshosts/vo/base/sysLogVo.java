package com.enorth.dns.dnshosts.vo.base;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/17
 * */

import java.util.Date;

public class sysLogVo {
    private int logId;
    private Date logDate = new Date();
    private String objectType;
    private int objectId;
    private String logDetail;
    private String modUserId;

    public sysLogVo() {
    }

    public sysLogVo(int logId, Date logDate, String objectType, int objectId, String logDetail, String modUserId) {
        this.logId = logId;
        this.logDate = logDate;
        this.objectType = objectType;
        this.objectId = objectId;
        this.logDetail = logDetail;
        this.modUserId = modUserId;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }

    public String getModUserId() {
        return modUserId;
    }

    public void setModUserId(String modUserId) {
        this.modUserId = modUserId;
    }
}
