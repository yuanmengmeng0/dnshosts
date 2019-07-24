package com.enorth.dns.dnshosts.serviceImpl.helper;

/**
 * Created by Administrator on 2017/5/18.
 */
public class UCenterCode {
    private int code;
    private UserBo result;

    public UCenterCode() {
    }

    public UCenterCode(int code, UserBo result) {
        this.code = code;
        this.result = result;
    }

    public UserBo getResult() {
        return result;
    }

    public void setResult(UserBo result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
