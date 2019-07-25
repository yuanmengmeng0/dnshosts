package com.enorth.dns.dnshosts.serviceImpl.helper;

import cn.com.enorth.utility.app.syslog.SysLogger;
import com.enorth.dns.dnshosts.consts.login.LoginConst;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by buce on 2017/6/26.
 */
@Component
public class RedirectHelper {

    private static RedirectHelper redirectHelper;
    private static SysLogger logger = SysLogger.getLogger(RedirectHelper.class);


    @PostConstruct
    public void init() {
        redirectHelper = this;
    }

    /**
     * 根据不同位置跳转不同链接
     *
     * @param projectPath 项目路径
     * @param relaystate  跳转位置
     * @return
     */
    public static String gerRedirectUrl(String projectPath, String relaystate) {
        StringBuilder redirectUrl = new StringBuilder(projectPath);
        relaystate = StringUtils.isEmpty(relaystate) ? LoginConst.APP_ID : relaystate;
        if (LoginConst.APP_ID.equals(relaystate)) {
//            redirectUrl.append("main/redirectAction!main.do");
            redirectUrl.append("showGroup");
        }
        return redirectUrl.toString();
    }

    public static Map<String, String> getPositions(String relaystate) {
        Map<String, String> result = new HashMap<>();
        String[] positions = relaystate.split("_");
        int length = positions.length;
        String appId = "-1";
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                appId = positions[i];
                result.put("appId", appId);
            }
        }
        return result;
    }

    public static String getRelaystate(HttpServletRequest request) {
        String appId = request.getParameter("appId");
        List<String> relaystateList = new ArrayList<>();
        if (StringUtils.isEmpty(appId) || LoginConst.APP_ID.equals(appId)) {
            relaystateList.add(LoginConst.APP_ID);
        } else {
            if (StringUtils.isNotEmpty(appId)) {
                relaystateList.add(appId);
            }
        }
        return StringUtils.join(relaystateList, "_");
    }

}