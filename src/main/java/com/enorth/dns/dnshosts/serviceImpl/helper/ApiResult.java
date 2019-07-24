package com.enorth.dns.dnshosts.serviceImpl.helper;


import com.enorth.dns.dnshosts.consts.EnumResult;

/**
 * Created by buce on 2017/5/24.
 */
public class ApiResult<T> {
    private int code;
    private T result;
    private String msg;

    public ApiResult() {
    }
    public ApiResult(EnumResult enumResult) {
        this.code = enumResult.getCode();
        this.msg = enumResult.getMsg();
    }

    public ApiResult(EnumResult enumResult, T result) {
        this.code = enumResult.getCode();
        this.result = result;
        this.msg = enumResult.getMsg();
    }

    public int getCode() {
        return code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
