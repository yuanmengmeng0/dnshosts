package com.enorth.dns.dnshosts.serviceImpl;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */

import com.enorth.dns.dnshosts.consts.AllConsts;
import com.enorth.dns.dnshosts.dao.CommonDao;
import com.enorth.dns.dnshosts.service.AllService;
import com.enorth.dns.dnshosts.vo.groupVo;
import com.enorth.dns.dnshosts.vo.hostsVo;
import com.enorth.dns.dnshosts.vo.sysLogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class AllServiceImpl implements AllService {
    private static final Logger log= LoggerFactory.getLogger(AllServiceImpl.class);
    @Autowired
    GroupServiceImpl groupService;
    @Autowired
    LogServiceImpl logService;
    @Autowired
    HostServiceImpl hostService;
    @Autowired
    private CommonDao commonDao;
    @Override
    public void insertGroupLog(groupVo gVo, sysLogVo sVo) throws Exception {
        this.groupService.insertGroup(gVo);
        int logId=this.getNextVal("sn_log");
        sVo.setLogId(logId);
        this.logService.insertLog(sVo);
    }

    @Override
    public void modifyGroupLog(groupVo gVo, sysLogVo sVo) throws Exception {
        this.groupService.modifyGroup(gVo);
        int logId=this.getNextVal("sn_log");
        sVo.setLogId(logId);
        this.logService.insertLog(sVo);
    }

    @Override
    public void insertHostLog(List<String> list, hostsVo hVo , sysLogVo sVo) throws Exception {
        for(String hostname:list){
            int hostId=this.getNextVal("sn_hosts");
            int listOrder=this.getNextVal("sn_hostListOrder");
            hVo.setHostsId(hostId);
            hVo.setListOrder(listOrder);
            hVo.setHostNames(hostname);
            int logId=this.getNextVal("sn_log");
            sVo.setLogId(logId);
            sVo.setObjectType("hosts");
            sVo.setObjectId(hVo.getHostsId());
            sVo.setModUserId(hVo.getModUserId());
            this.hostService.insertHost(hVo);
            this.logService.insertLog(sVo);
        }

    }

    @Override
    public void insertHostLog(hostsVo hVo) throws Exception {
        this.hostService.insertHost(hVo);
        sysLogVo sysLogVo=new sysLogVo();
        int logId=this.getNextVal("sn_log");
        sysLogVo.setLogId(logId);
        sysLogVo.setLogDate(new Date());
        sysLogVo.setObjectType("hosts");
        sysLogVo.setObjectId(hVo.getHostsId());
        if(hVo.getState() == AllConsts.stateOpen){
            sysLogVo.setLogDetail(hVo.getModUserName()+"导入解析["+ hVo.getIpAddress()+"]["+ hVo.getHostNames()+"],状态[启用],备注["+ hVo.getMemo()+"]");
        }else{
            sysLogVo.setLogDetail(hVo.getModUserName()+"导入解析["+ hVo.getIpAddress()+"]["+ hVo.getHostNames()+"],状态[禁用],备注["+ hVo.getMemo()+"]");
        }
        sysLogVo.setModUserId(hVo.getModUserId());
        this.logService.insertLog(sysLogVo);

    }

    @Override
    public void modifyHostLog(hostsVo hVo, sysLogVo sVo) throws Exception {
        this.hostService.modifyHost(hVo);
        int logId=this.getNextVal("sn_log");
        sVo.setLogId(logId);
        this.logService.insertLog(sVo);
    }

    @Override
    public int getNextVal(String name) throws Exception {
        int bd=this.commonDao.getNextVal(name);
        return bd;
    }

    /*获取登录信息*/
    public Object getUser(HttpServletRequest request){
        request.getSession().setAttribute("_session_user_key","555");
        Object obj=request.getSession().getAttribute(AllConsts.SESSION_USER_NAME);
        if(obj != null){
            return obj;
        }else{
            return null;
        }
    }


}
