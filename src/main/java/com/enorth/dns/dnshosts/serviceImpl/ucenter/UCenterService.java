package com.enorth.dns.dnshosts.serviceImpl.ucenter;

import cn.com.enorth.sso.util.MD5;
import cn.com.enorth.ucenter.UCB;
import cn.com.enorth.ucenter.bean.ResultVo;
import cn.com.enorth.ucenter.bean.UserInfoVo;
import cn.com.enorth.utility.Beans;
import cn.com.enorth.utility.app.syslog.SysLogger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.enorth.dns.dnshosts.consts.login.LoginConst;
import com.enorth.dns.dnshosts.serviceImpl.helper.UCenterCode;
import com.enorth.dns.dnshosts.serviceImpl.helper.UserBo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by buce on 2017/5/18.
 */
public class UCenterService {

    private static SysLogger logger = SysLogger.getLogger(UCenterService.class);

    public static UCenterCode getUserFromUCenter(String refreshToken) throws Exception {
        String url = LoginConst.GET_USER_DETAIL_REFRESH_TOKEN_URL;
        String check_sum = MD5.compute(refreshToken + LoginConst.APP_ID + LoginConst.APP_SEED);
        url += ("?appId=" + LoginConst.APP_ID + "&api_token=" + refreshToken + "&check_sum=" + check_sum);
        System.out.println(url);
        String result = Beans.httpUtil.httpGet(url, new HashMap<String, String>(), "utf-8", 5000, 5000);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        int returnCode = Integer.parseInt(jsonObject.get("code").toString());
        UCenterCode code = new UCenterCode(-1, new UserBo());
        if (returnCode == 1) {
            code = JSON.parseObject(result, UCenterCode.class);
        }
        return code;
    }

    public static Map<Long, String> getUsersNickname(List<Long> userIds) {
        String appId = LoginConst.APP_ID;
        Map<Long, String> resultMap = new HashMap<Long, String>();
        try {
            ResultVo userInfoByIds = UCB.userInfoUtil().getUserInfoByIds(userIds, appId);
            if (userInfoByIds.getCode() == 1) {
                List<UserInfoVo> userInfoVoList = userInfoByIds.getUserInfoVoList();
                for (UserInfoVo vo : userInfoVoList) {
                    resultMap.put(vo.getUserId(), vo.getNickname());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultMap;
    }
}
