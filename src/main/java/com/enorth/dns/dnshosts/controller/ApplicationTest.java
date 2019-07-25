package com.enorth.dns.dnshosts.controller;/*
 * @author  Administrator
 * @description:
 * @date 2019/7/17
 * */

import cn.com.enorth.global.consts.GlobalConsts;
import cn.com.enorth.ucenter.UCB;
import cn.com.enorth.utility.AppBeans;
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
            AppBeans.getGlobalUtil().addParam(DEFAULT_PROGNAME, key, value);
            // 再灌系统参数
            SysParamInit.getInstance().putLocalParam(key, value);
        }
        System.out.println("=============结束=================");
        initBus();
    }

    private void initBus() throws Exception {
        String progName = DBProgNameConst.getPROGNAME();
        String busOn = AppBeans.getGlobalUtil().getParamValue(progName, GlobalConsts.GLOBAL_PARAM_KEY_BUS_ON);
        if (new Boolean(busOn)) {
            System.out.println("init bus...");
            String busDb = AppBeans.getGlobalUtil().getParamValue(progName, GlobalConsts.GLOBAL_PARAM_KEY_BUS_DB);
            String progId = AppBeans.getGlobalUtil().getParamValue(progName, GlobalConsts.GLOBAL_PARAM_KEY_BUS_PROG_ID);
            AppBeans.getEpBusCenter().init(busDb, progId);
            System.out.println("init bus success!!!");
            System.out.println("初始化用户中心jar");
//            String uCenterInnerProgName = AppBeans.getGlobalUtil().getParamValue(DBProgNameConst.getPROGNAME(),
//                    GlobalConsts.CLIENT_PROG_NAME_UCENTER_INNER + ParamConst.PARAM_NAME_POINT + GlobalConsts.GLOBAL_PARAM_KEY_PROG_NAME);
            String uCenterBusId = AppBeans.getGlobalUtil().getParamValue(DBProgNameConst.getPROGNAME(),
                    GlobalConsts.CLIENT_PROG_NAME_UCENTER_INNER + ParamConst.PARAM_NAME_POINT + GlobalConsts.GLOBAL_PARAM_KEY_BUS_ID);
            UCB.paramHelper().init(uCenterBusId, false, "", "", 0);
            System.out.println("初始化用户中心jar成功");
        }
    }
}
