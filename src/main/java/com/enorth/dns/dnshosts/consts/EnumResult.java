package com.enorth.dns.dnshosts.consts;

/**
 * Created by Administrator on 2017/5/24.
 */
public enum EnumResult {

    SUCCESS(1, "操作成功"),
    FAIL(2, "操作失败"),
    LOGIN_FAIL(-1,"登录失败"),
    PARAM_ERR(3,"参数异常"),
    ;
    private int code;
    private String msg;

    EnumResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
