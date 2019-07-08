package com.enorth.dns.dnshosts.service;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */


import com.enorth.dns.dnshosts.vo.sysLogVo;

public interface LogService {
    void insertLog(sysLogVo vo) throws  Exception;
}
