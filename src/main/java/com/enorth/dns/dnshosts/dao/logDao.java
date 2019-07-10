package com.enorth.dns.dnshosts.dao;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */


import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.base.PhotoVo;
import com.enorth.dns.dnshosts.vo.sysLogVo;

import java.util.List;

public interface logDao {
    void insertLog(sysLogVo vo);
    List<sysLogVo> getAllLog();
    List<sysLogVo> getAllLogs(Page<sysLogVo> page);
    void insertPhoto(PhotoVo vo);
    List<PhotoVo> getPhotos();
}
