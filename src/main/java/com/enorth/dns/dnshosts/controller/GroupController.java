package com.enorth.dns.dnshosts.controller;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/17
 * */


import cn.com.enorth.utility.app.web.strutsx.impl.annotations.RequstPath;
import com.enorth.dns.dnshosts.consts.AllConsts;
import com.enorth.dns.dnshosts.serviceImpl.AllServiceImpl;
import com.enorth.dns.dnshosts.serviceImpl.EtcHostServiceImpl;
import com.enorth.dns.dnshosts.serviceImpl.GroupServiceImpl;
import com.enorth.dns.dnshosts.serviceImpl.LogServiceImpl;
import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.groupVo;
import com.enorth.dns.dnshosts.vo.sysLogVo;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class GroupController {
    private static final Logger log= LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupServiceImpl groupService;
    @Autowired
    private AllServiceImpl allService;
    @Autowired
    private LogServiceImpl logService;
    @Autowired
    private EtcHostServiceImpl etcHostService;


    private Object obj;



/*查询所有组*/
    @RequestMapping("/showGroup")
    public String showGroup(HttpServletRequest request) throws Exception {
        String gname=request.getParameter("groName");
        String pageNo=null;
        pageNo=request.getParameter("pageNo");
        if(StringUtils.isEmpty(pageNo)){
            pageNo = "1";
        }
        Page<groupVo> page= new Page<>();
        page.setPageNo(Integer.valueOf(pageNo));
        groupVo vo=new groupVo();
        vo.setGroupName(gname);
        /*为了显示查询条件*/
        request.setAttribute("vo",vo);
        List<groupVo> list=new ArrayList<>();
        if(gname == null || gname == ""){
            /*显示所有*/
//           list=this.groupService.getAllGroup();
            page = this.groupService.getGroups(page);
        }else{
            /*模糊查询*/
//            list=this.groupService.getLikeGroup(vo);
            page=this.groupService.getLikeGroups(vo,page);
        }
         request.setAttribute("groupVo",page);
        return "mainGroup";
    }
    //显示修改的数据
    @RequestMapping(value = "/modify")
    public void getGroups(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        JSONObject json=new JSONObject();
        PrintWriter out=response.getWriter();
        groupVo gvo=new groupVo();
        String gid=request.getParameter("gid");
        if(gid == null){

        }else{
            gvo=this.groupService.getGroupById(Integer.valueOf(gid));
            json.put("gvo",gvo);
        }
        out.print(json);
        out.flush();
        out.close();
    }


    /*
   * 创建组
   **/
    @RequestMapping(value = "/groupSubmit",method = RequestMethod.POST)
    public String group(HttpServletRequest request, HttpServletResponse response, groupVo gvo) throws Exception {
        obj = this.allService.getUser(request);


        response.setCharacterEncoding("UTF-8");
        int groupId=this.allService.getNextVal("sn_group");
        int listOrder=this.allService.getNextVal("sn_groupListOrder");
        gvo.setGroupId(groupId);
        gvo.setListOrder(listOrder);
        gvo.setModUserId("11");
        gvo.setModUserName("uuu");
        gvo.setModDate(new Date());
        sysLogVo svo =this.logService.message("group",gvo.getGroupId(),gvo.getModUserId());
        if(StringUtils.isEmpty(gvo.getGroupName())){
            throw new Exception("请填写组名称");
        }else {
            if (gvo.getState() == AllConsts.stateOpen) {
                svo.setLogDetail(gvo.getModUserName() + "创建组" + "[" + gvo.getGroupName() + "]" + ",状态[启用]");
            } else {
                svo.setLogDetail(gvo.getModUserName() + "创建组" + "[" + gvo.getGroupName() + "]" + ",状态[禁用]");
            }
            //往group表和log表插入数据
            this.allService.insertGroupLog(gvo, svo);
            //生成hosts文件
            List<groupVo> listGroup = this.groupService.getAllGroup();
            boolean create=this.etcHostService.createHost(listGroup);
            if(create){
                log.info("服务重启开始了");
//                this.etcHostService.LinuxExe();
                log.info("服务重启结束");
            }
        }
        return "redirect:/showGroup";
    }

    /*
     * 修改组
     **/
    @RequestMapping(value = "/groupModify",method = RequestMethod.POST)
    public String modifyGroup(HttpServletRequest request, HttpServletResponse response, groupVo gvo,Page<groupVo> page) throws Exception {
        //准备数据  开始
        gvo.setModUserId("11");
        gvo.setModUserName("uuu");
        gvo.setModDate(new Date());
        sysLogVo svo =this.logService.message("group",gvo.getGroupId(),gvo.getModUserId());
        groupVo vo=this.groupService.getGroupById(gvo.getGroupId());
        if(StringUtils.isEmpty(gvo.getGroupName())){
            throw new Exception("请输入组名称");
        }else {
            if (vo.getState() == AllConsts.stateOpen) {
                if (gvo.getState() == AllConsts.stateOpen) {
                    svo.setLogDetail(gvo.getModUserName() + "修改组" + "[" + vo.getGroupName() + "]新名称[" + gvo.getGroupName() + "],原状态[启用]新状态[启用]");
                } else {
                    svo.setLogDetail(gvo.getModUserName() + "修改组" + "[" + vo.getGroupName() + "]新名称[" + gvo.getGroupName() + "],原状态[启用]新状态[禁用]");
                }

            } else {
                if (gvo.getState() == AllConsts.stateOpen) {
                    svo.setLogDetail(gvo.getModUserName() + "修改组" + "[" + vo.getGroupName() + "]新名称[" + gvo.getGroupName() + "],原状态[禁用]新状态[启用]");
                } else {
                    svo.setLogDetail(gvo.getModUserName() + "修改组" + "[" + vo.getGroupName() + "]新名称[" + gvo.getGroupName() + "],原状态[禁用]新状态[禁用]");
                }
            }
            //结束

           // 往group表和log表插入数据
            this.allService.modifyGroupLog(gvo, svo);
           // 生成hosts文件
            List<groupVo> listGroup = this.groupService.getAllGroup();
            boolean create=this.etcHostService.createHost(listGroup);
            if(create){
                log.info("服务重启开始了");
//                this.etcHostService.LinuxExe();
                log.info("服务重启结束");
            }
        }
        return "redirect:/showGroup";
    }

    /*
    * 删除组
    **/
    @RequestMapping(value = "/delGroup",method = RequestMethod.POST)
    public void delGroup(HttpServletRequest request, HttpServletResponse response, groupVo vo) throws Exception {
        PrintWriter out=response.getWriter();
        JSONObject json=new JSONObject();
        json.put("code",-1);
        groupVo gvo=this.groupService.getGroupById(vo.getGroupId());
        gvo.setModUserId("11");
        gvo.setModUserName("uuu");
        vo.setModDate(new Date());
        sysLogVo svo=this.logService.message("group",vo.getGroupId(),vo.getModUserId());
        int logId=this.allService.getNextVal("sn_log");
        svo.setLogId(logId);
        try {
            this.groupService.delGroup(vo);
            json.put("code",1);
            svo.setLogDetail(vo.getModUserName()+"删除组["+gvo.getGroupName()+"]");
            this.logService.insertLog(svo);
           // 生成hosts文件
            List<groupVo> listGroup = this.groupService.getAllGroup();
            boolean create=this.etcHostService.createHost(listGroup);
            if(create){
                log.info("服务重启开始了");
//                this.etcHostService.LinuxExe();
                log.info("服务重启结束");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.print(json);
        out.flush();
        out.close();
    }

    /*
    * 启用/禁用组
    **/
    @RequestMapping(value = "/updateGroup",method = RequestMethod.POST)
    public void updateGroup(HttpServletRequest request, HttpServletResponse response, groupVo vo) throws Exception {
        PrintWriter out=response.getWriter();
        JSONObject json=new JSONObject();
        json.put("code",-1);
        groupVo gvo=this.groupService.getGroupById(vo.getGroupId());

        vo.setModUserId("111");
        vo.setModUserName("aaa");
        vo.setModDate(new Date());
        try {
            sysLogVo svo =this.logService.message("group",vo.getGroupId(),vo.getModUserId());
            int logId=this.allService.getNextVal("sn_log");
            svo.setLogId(logId);
            if(gvo.getState() == AllConsts.stateOpen){
                vo.setState(AllConsts.stateClose);
                this.groupService.updateGroup(vo);
                svo.setLogDetail(vo.getModUserName()+"修改组["+gvo.getGroupName()+"],原状态[启用]新状态[禁用]");
            }else{
                vo.setState(AllConsts.stateOpen);
                this.groupService.updateGroup(vo);
                svo.setLogDetail(vo.getModUserName()+"修改组["+gvo.getGroupName()+"],原状态[禁用]新状态[启用]");
            }
            this.logService.insertLog(svo);
            //生成hosts文件
            List<groupVo> listGroup = this.groupService.getAllGroup();
            boolean create=this.etcHostService.createHost(listGroup);
            if(create){
                System.out.println("================服务重启开始了"+System.nanoTime()+"===================");
//                this.etcHostService.LinuxExe();
                System.out.println("================服务重启结束"+System.nanoTime()+"======================");
            }
            json.put("code",1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.print(json);
        out.flush();
        out.close();
    }

    /*
    * 导出hosts文件
    * */
    @RequestMapping(value = "/export")  /*第一种*/
    public ResponseEntity<byte[]> export(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String path = request.getServletContext().getRealPath("f:/drivers/etc");
        File file=new File("f:/drivers/etc/hosts");
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String("hosts".getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);

    }
    /*@RequestMapping(path = "/export")  *//*第二种*//*
    public void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String( "hosts".getBytes("ISO8859-1"), "utf-8" ) );
        List<?> hostsData = this.etcHostService.getHostsData();
        for(Object obj:hostsData){
            String line=obj.toString();
            response.getWriter().println(line);
        }
        response.getWriter().flush();
        response.getWriter().close();

    }*/

}
