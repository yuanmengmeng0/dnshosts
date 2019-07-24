package com.enorth.dns.dnshosts.consts;

/**
 * 
 * @author mu bingxin
 *
 */
public class DBProgNameConst {
    
    //此处的值 CMS的值在系统启动init的时候会被赋值
    private static String PROGNAME ="dnshosts";
    
    public static String RESOURCE = "resource";
    
    public static String LOG = "log";
    
    public static String REC="rec";
    

    
    public static String ME() {
        return PROGNAME;
    }


    public static String getPROGNAME() {
        return PROGNAME;
    }
    public static void setPROGNAME(String pROGNAME) {
        PROGNAME = pROGNAME;
    }
}
