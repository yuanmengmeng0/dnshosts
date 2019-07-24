package com.enorth.dns.dnshosts.consts.login;

import cn.com.enorth.global.consts.GlobalConsts;
import cn.com.enorth.utility.AppBeans;
import cn.com.enorth.utility.app.syslog.SysLogger;
import com.enorth.dns.dnshosts.consts.DBProgNameConst;
import com.enorth.dns.dnshosts.consts.ParamConst;

/**
 * 登录用常量
 * Created by buce on 2017/5/18.
 */
public class LoginConst {
    public static String UCENTER_HOST = "";
    private static SysLogger logger = SysLogger.getLogger(LoginConst.class);

    static {
        try {
            String uCenterInnerProgName = AppBeans.getGlobalUtil().getParamValue(DBProgNameConst.getPROGNAME(),
                    GlobalConsts.CLIENT_PROG_NAME_UCENTER_INNER + ParamConst.PARAM_NAME_POINT + GlobalConsts.GLOBAL_PARAM_KEY_PROG_NAME);
            UCENTER_HOST = AppBeans.getGlobalUtil().getParamValue(uCenterInnerProgName, GlobalConsts.GLOBAL_PARAM_KEY_PROG_ROOT);//用户中心
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

//    public static String APP_ID = CKB.paramHelper().getString("dnshosts.app_id");
//    public static String APP_SEED = CKB.paramHelper().getString("dnshosts.app_seed");  查询tsen_sys_param 表中的数据
    public static String APP_ID;
    public static String APP_SEED;

    static {
        try {
            APP_ID = AppBeans.getGlobalUtil().getParamValue(DBProgNameConst.getPROGNAME(), "app_id");
            APP_SEED = AppBeans.getGlobalUtil().getParamValue(DBProgNameConst.getPROGNAME(), "app_seed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String DNSHOSTS_COOKIE_NAME = "cookie_dnshosts_refresh_token";
    public static String UCENTER_LOGIN_URL = UCENTER_HOST + "/user_center/UserCenterAction!toLogin.do?appId=" + APP_ID +
            "&phoneTokenCheckFlg=false&RELAYSTATE=";
    public static String GET_USER_DETAIL_AUTHCODE_URL =
            UCENTER_HOST + "/user_center/UserCenterAction!getUserDetailAuthCode.do";
    public static String GET_USER_DETAIL_REFRESH_TOKEN_URL =
            UCENTER_HOST + "/r/api/v1.0/getUserDetailRefreshToken";
//            UCENTER_HOST + "/user_center/UserCenterAction!getUserDetailRefreshToken.do";
    public static String QUITE_ALL_LOGIN_URL =
            UCENTER_HOST + "/user_center/UserCenterAction!quiteAllLogin.do";


}
