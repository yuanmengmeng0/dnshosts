package com.enorth.dns.dnshosts.serviceImpl;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */


import com.enorth.dns.dnshosts.dao.logDao;
import com.enorth.dns.dnshosts.service.LogService;
import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.base.PhotoVo;
import com.enorth.dns.dnshosts.vo.sysLogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class LogServiceImpl implements LogService {
    private static final Logger log= LoggerFactory.getLogger(LogServiceImpl.class);
    @Autowired
    private logDao logDao;

    @Override
    public void insertLog(sysLogVo vo) throws Exception {
        this.logDao.insertLog(vo);
    }

    @Override
    public List<sysLogVo> getAllLog() throws Exception {
        List<sysLogVo> list=new ArrayList<>();
        list=this.logDao.getAllLog();
        return list;
    }

    @Override
    public Page<sysLogVo> getAllLogs(Page<sysLogVo> page) throws Exception {
        List<sysLogVo> list=new ArrayList<>();
        list=this.logDao.getAllLogs(page);
        Page<sysLogVo> pages = new Page<>();
        pages.setPageNo(page.getPageNo());
        pages.setResults(list);
        pages.setTotalRecord(this.getAllLog().size());
        return pages;
    }

    public sysLogVo message(String type, int objectId, String modUserId){
        sysLogVo svo=new sysLogVo();
        svo.setLogDate(new Date());
        svo.setObjectType(type);
        svo.setObjectId(objectId);
        svo.setModUserId(modUserId);
        return svo;
    }

    public void insertPhoto(sysLogVo vo) {
        for(PhotoVo photoVo:vo.getPhotos()){
            this.logDao.insertPhoto(photoVo);
        }
    }

    public List<PhotoVo> getPhotos() {
        List<PhotoVo> list=new ArrayList<>();
        list = this.logDao.getPhotos();
        return list;
    }
}
