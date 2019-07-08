package com.enorth.dns.dnshosts.serviceImpl;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */


import com.enorth.dns.dnshosts.dao.logDao;
import com.enorth.dns.dnshosts.service.LogService;
import com.enorth.dns.dnshosts.vo.sysLogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    public sysLogVo message(String type, int objectId, String modUserId){
        sysLogVo svo=new sysLogVo();
        svo.setLogDate(new Date());
        svo.setObjectType(type);
        svo.setObjectId(objectId);
        svo.setModUserId(modUserId);
        return svo;
    }
}
