package com.enorth.dns.dnshosts.controller;/*
 * @author  Administrator
 * @description:
 * @date 2019/7/9
 * */

import com.enorth.dns.dnshosts.service.LogService;
import com.enorth.dns.dnshosts.serviceImpl.LogServiceImpl;
import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.base.PhotoVo;
import com.enorth.dns.dnshosts.vo.sysLogVo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class SysLogController {
    private static final Logger log= LoggerFactory.getLogger(SysLogController.class);
    @Autowired
    private LogServiceImpl logService;
    @RequestMapping("/showLog")
    public String showSysLog(HttpServletRequest request) throws Exception {
//        List<sysLogVo> list=this.logService.getAllLog();
        String pageNo=null;
        pageNo=request.getParameter("pageNo");
        if(StringUtils.isEmpty(pageNo)){
            pageNo = "1";
        }
        Page<sysLogVo> page= new Page<>();
        page.setPageNo(Integer.valueOf(pageNo));
        page = this.logService.getAllLogs(page);
        request.setAttribute("page",page);
        List<PhotoVo> list = this.logService.getPhotos();
        request.setAttribute("photos",list);
        return "sysLog";
    }

    @RequestMapping(value = "/imgs",method = RequestMethod.POST)
    public String imgSub(sysLogVo vo){
        this.logService.insertPhoto(vo);
        return "redirect:showLog";
    }
}
