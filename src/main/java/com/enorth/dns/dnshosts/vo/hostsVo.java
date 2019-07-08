package com.enorth.dns.dnshosts.vo;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/17
 * */

import java.io.Serializable;

public class hostsVo extends com.enorth.dns.dnshosts.vo.base.hostsVo implements Serializable {
    private String ipHost;

    public String getIpHost() {
        return ipHost;
    }

    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }
}
