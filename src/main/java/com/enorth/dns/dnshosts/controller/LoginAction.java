package com.enorth.dns.dnshosts.controller;


import cn.com.enorth.sso.util.HttpUtil;
import cn.com.enorth.sso.util.MD5;
import cn.com.enorth.utility.app.web.strutsx.impl.annotations.RequstPath;
import cn.com.enorth.utility.app.web.strutsx.impl.annotations.ResponseFormat;
import cn.com.enorth.utility.app.web.strutsx.impl.service.action.StrutsXActionSupport;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.enorth.dns.dnshosts.consts.login.LoginConst;
import com.enorth.dns.dnshosts.serviceImpl.helper.RedirectHelper;
import com.enorth.dns.dnshosts.serviceImpl.helper.UCenterUserResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;


/**
 * 登录类
 * Created by buce on 2017/5/18.
 */
@Controller
public class LoginAction extends StrutsXActionSupport {

    /**
     * 用户中心登录回调
     * http://域名:端口/dnshosts/r/login/v1/UCenterLoginRecall
     */
   @RequestMapping(value = "/UCenterLoginRecall")
    public void UCenterLoginRecall(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String authCode = request.getParameter("authCode");
        String relaystate = request.getParameter("RELAYSTATE");
        relaystate = StringUtils.isEmpty(relaystate) ? LoginConst.APP_ID : relaystate;
        String projectPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        String check_sum = MD5.compute(LoginConst.APP_ID + authCode + LoginConst.APP_SEED);
        HttpUtil httpUtil = new HttpUtil();
        Properties params = new Properties();
        params.setProperty("appId", LoginConst.APP_ID);
        params.setProperty("authCode", authCode);
        params.setProperty("check_sum", check_sum);
        String result = new String(httpUtil.post(LoginConst.GET_USER_DETAIL_AUTHCODE_URL, params, "utf-8"), "utf-8");
        UCenterUserResult uCenterUserResult = null;
        JSONObject jsonObject = JSONObject.parseObject(result);
        if ("1".equals(jsonObject.get("code"))) {
            uCenterUserResult = JSON.parseObject(result, UCenterUserResult.class);
        } else {
            response.sendRedirect(LoginConst.UCENTER_LOGIN_URL + relaystate);
            return;
        }
        Cookie cookie = new Cookie(LoginConst.DNSHOSTS_COOKIE_NAME, uCenterUserResult.getResult().getRefreshToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        System.out.println(uCenterUserResult.toString());
        System.out.println("RELAYSTATE= " + relaystate);
        String redirectUrl = RedirectHelper.gerRedirectUrl(projectPath, relaystate);
        System.out.println("跳转地址: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    /**
     * 用户登出
     */

    @ResponseFormat(type = "json")
    @RequstPath(path = "/", method = "get")
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        String refreshToken = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LoginConst.DNSHOSTS_COOKIE_NAME)) {
                refreshToken = cookie.getValue();
                cookie.setMaxAge(0);
                break;
            }
        }
        String check_sum = MD5.compute(LoginConst.APP_ID + refreshToken + LoginConst.APP_SEED);
        StringBuilder url = new StringBuilder(LoginConst.QUITE_ALL_LOGIN_URL);
        url.append("?refreshToken=");
        url.append(refreshToken);
        url.append("&appId=");
        url.append(LoginConst.APP_ID);
        url.append("&check_sum=");
        url.append(check_sum);
        response.sendRedirect(url.toString());
    }
}
