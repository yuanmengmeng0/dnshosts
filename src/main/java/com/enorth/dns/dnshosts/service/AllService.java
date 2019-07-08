package com.enorth.dns.dnshosts.service;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */



import com.enorth.dns.dnshosts.vo.groupVo;
import com.enorth.dns.dnshosts.vo.hostsVo;
import com.enorth.dns.dnshosts.vo.sysLogVo;

import java.util.List;

public interface AllService {
    void insertGroupLog(groupVo gVo, sysLogVo sVo) throws Exception;
    void modifyGroupLog(groupVo gVo, sysLogVo sVo) throws Exception;
    void insertHostLog(List<String> list, hostsVo hVo, sysLogVo sVo) throws  Exception;
    void insertHostLog(hostsVo hVo) throws  Exception;
    void modifyHostLog(hostsVo hVo, sysLogVo sVo) throws Exception;


    /*序列*/
    int getNextVal(String name) throws Exception;
}

