package com.enorth.dns.dnshosts.service;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */


import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.sysLogVo;

import java.util.List;

public interface LogService {
    void insertLog(sysLogVo vo) throws  Exception;
    List<sysLogVo> getAllLog() throws Exception;
    Page<sysLogVo> getAllLogs(Page<sysLogVo> page) throws Exception;
}
