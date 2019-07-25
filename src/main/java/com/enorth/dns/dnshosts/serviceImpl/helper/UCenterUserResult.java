package com.enorth.dns.dnshosts.serviceImpl.helper;

/**
 * Created by Administrator on 2017/5/18.
 */
public class UCenterUserResult {
    private String code;
    private UserBo result;


    public UCenterUserResult() {
    }

    public UCenterUserResult(String code, UserBo result) {
        this.code = code;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserBo getResult() {
        return result;
    }

    public void setResult(UserBo result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UCenterUserResult{" +
                "code='" + code + '\'' +
                ", result=" + result +
                '}';
    }
}
