package com.enorth.dns.dnshosts.filter;/*
 * @author  Administrator
 * @description:
 * @date 2019/7/24
 * */

import com.alibaba.fastjson.JSON;
import com.enorth.dns.dnshosts.consts.EnumResult;
import com.enorth.dns.dnshosts.consts.login.LoginConst;
import com.enorth.dns.dnshosts.serviceImpl.helper.ApiResult;
import com.enorth.dns.dnshosts.serviceImpl.helper.RedirectHelper;
import com.enorth.dns.dnshosts.serviceImpl.helper.UCenterCode;
import com.enorth.dns.dnshosts.serviceImpl.ucenter.UCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
@WebFilter(urlPatterns = "/dnshosts/filter/*",filterName = "UserFilter")
public class UserFilter implements Filter {
    private static final Logger log= LoggerFactory.getLogger(UserFilter.class);
    private static Set<String> noFilterMethodName = new HashSet<>();

    static {
        noFilterMethodName.add("UCenterLoginRecall");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("1");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        String requestURI = httpServletRequest.getRequestURI();
        String relaystate = RedirectHelper.getRelaystate(httpServletRequest);
        String redirectURL = LoginConst.UCENTER_LOGIN_URL + relaystate;
        //是否为过滤的方法
        boolean isContains = false;
        for (String methodName : noFilterMethodName) {
            if (requestURI.contains(methodName)) {
                isContains = true;
                break;
            }
        }
        if (!isContains) {//方法需要过滤 取cookie验证
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies == null) { //cookie不存在
                if (requestURI.contains("/main")) {
                    httpServletResponse.sendRedirect(redirectURL);
                } else {
                    httpServletResponse.getWriter().write(JSON.toJSONString(new ApiResult<String>(EnumResult.LOGIN_FAIL,redirectURL)));
                }
                return;
            }
            String refreshToken = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LoginConst.DNSHOSTS_COOKIE_NAME)) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
            if (refreshToken == null) {
                httpServletResponse.sendRedirect(redirectURL);
                return;
            }
            UCenterCode code = null;
            try {
                code = UCenterService.getUserFromUCenter(refreshToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (code.getCode() < 0) {//cookie存在,验证cookie
                if (requestURI.contains("/main")) {
                    httpServletResponse.sendRedirect(redirectURL);
                } else {
                    httpServletResponse.getWriter().write(JSON.toJSONString(new ApiResult<String>(EnumResult.LOGIN_FAIL,redirectURL)));
                }
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
