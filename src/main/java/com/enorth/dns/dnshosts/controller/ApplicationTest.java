package com.enorth.dns.dnshosts.controller;/*
 * @author  Administrator
 * @description:
 * @date 2019/7/17
 * */

import com.enorth.dns.dnshosts.consts.DBProgNameConst;
import com.enorth.dns.dnshosts.consts.ParamConst;
import com.enorth.dns.dnshosts.consts.SysParamInit;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ApplicationTest implements ApplicationRunner {
    private static String DEFAULT_PROGNAME = "dnshosts";
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============系统初始化开始=================");
        ResourceBundle rb = ResourceBundle.getBundle("sensitive");
        Enumeration<String> keys = rb.getKeys();
        Set<String> keySet = new HashSet<>();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            keySet.add(key);
            if (key.equals(ParamConst.PROG_NAME)) {
                DBProgNameConst.setPROGNAME(rb.getString(key));
            }
        }
        DEFAULT_PROGNAME = DBProgNameConst.getPROGNAME();
        for (String key : keySet) {
            String value = rb.getString(key);
            // 先灌global表
//            AppBeans.getGlobalUtil().addParam(DEFAULT_PROGNAME, key, value);
            // 再灌系统参数
            SysParamInit.getInstance().putLocalParam(key, value);
        }
        System.out.println("=============结束=================");
    }
}
