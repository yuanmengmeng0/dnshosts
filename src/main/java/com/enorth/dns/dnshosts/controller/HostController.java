package com.enorth.dns.dnshosts.controller;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/17
 * */


import com.enorth.dns.dnshosts.consts.AllConsts;
import com.enorth.dns.dnshosts.serviceImpl.*;
import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.groupVo;
import com.enorth.dns.dnshosts.vo.hostsVo;
import com.enorth.dns.dnshosts.vo.sysLogVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HostController {
    private static final Logger log= LoggerFactory.getLogger(HostController.class);

    @Autowired
    private LogServiceImpl logService;
    @Autowired
    private AllServiceImpl allService;
    @Autowired
    private HostServiceImpl hostService;
    @Autowired
    private GroupServiceImpl groupService;
    @Autowired
    private EtcHostServiceImpl etcHostService;



    @RequestMapping("/showAnalysis")
    public String analysis(HttpServletRequest request, hostsVo vo) throws Exception {

       /* String groupId = request.getParameter("groupId");*/
        List<hostsVo> list=null;
        boolean address=StringUtils.isEmpty(vo.getIpAddress());
        boolean hostname=StringUtils.isEmpty(vo.getHostNames());
        /*为了保留查询条件*/
        request.setAttribute("vo",vo);

        String pageNo=null;
        pageNo=request.getParameter("pageNo");
        if(StringUtils.isEmpty(pageNo)){
            pageNo = "1";
        }
        Page<hostsVo> page= new Page<>();
        page.setPageNo(Integer.valueOf(pageNo));
        if(address && hostname){
//            list = this.hostService.getAllHosts(vo.getGroupId());
            page=this.hostService.getAllHost(page,vo.getGroupId());
        }else {
//           list=this.hostService.getLikeHost(vo);
           page=this.hostService.getLikeHosts(vo,page);
        }
        request.setAttribute("hosts",page);
        return "analysis";
    }
    /*
     * 创建解析
     * */
    @RequestMapping(value = "/hostSubmit",method = RequestMethod.POST)
    public void group(HttpServletRequest request, HttpServletResponse response, hostsVo hvo) throws Exception {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        json.put("code", 1);
        if (StringUtils.isEmpty(hvo.getIpAddress())) {
            json.put("code", -1);
            json.put("mes", "请输入IP地址");
        } else if (StringUtils.isEmpty(hvo.getHostNames())) {
            json.put("code", -1);
            json.put("mes", "请输入解析域名");
        } else if (StringUtils.isEmpty(hvo.getMemo())) {
            json.put("code", -1);
            json.put("mes", "请输入备注");
        }
        else {
            /*准备的数据开始*/

            hvo.setModUserId("111");
            hvo.setModUserName("ooo");
            hvo.setModDate(new Date());
            sysLogVo svo = new sysLogVo();
            String memo = hvo.getMemo().replace("\n", " ");
            hvo.setMemo(memo);
            String host = hvo.getHostNames().replace("\n", " ");
            String[] hostNames = host.split(" ");
            List<String> list = new ArrayList<>();
            for (String str : hostNames) {
                if (!str.equals("")) {
                        list.add(str);
                }
            }

            if (hvo.getState() == AllConsts.stateOpen) {
                svo.setLogDetail(hvo.getModUserName() + "创建解析[" + hvo.getIpAddress() + "][" + hvo.getHostNames() + "],状态[启用],备注[" + hvo.getMemo() + "]");
            } else {
                svo.setLogDetail(hvo.getModUserName() + "创建解析[" + hvo.getIpAddress() + "][" + hvo.getHostNames() + "],状态[禁用],备注[" + hvo.getMemo() + "]");
            }
            /*结束*/


            List<hostsVo> listHosts=this.hostService.getAllHosts(hvo.getGroupId());
            for (String str : list) {
                boolean isExist = this.hostService.isExist(listHosts, hvo.getIpAddress(), str);
                if (isExist) {
                    json.put("code", -1);
                    json.put("mes", hvo.getIpAddress() + "-" + str + "已存在");
                    out.print(json);
                    out.flush();
                    out.close();
                    /*如果存在下面的代码不执行*/
                    return;
                }
            }

            if (hvo.getIpVersion() == AllConsts.Ipv4) {
                    boolean ipv4 = this.hostService.isIPv4(hvo.getIpAddress());
                    if (!ipv4) {
                        json.put("code", -1);
                        json.put("mes", "ip地址不合法");
                    }else{
                        this.allService.insertHostLog(list, hvo, svo);
                        /*生成hosts文件*/
                        List<groupVo> listGroup = this.groupService.getAllGroup();
                        /*boolean create=this.etcHostService.createHost(listGroup);
                        if(create){
                            log.info("服务重启开始了");
                            this.etcHostService.LinuxExe();
                            log.info("服务重启结束");
                        }*/
                    }
            } else if (hvo.getIpVersion() == AllConsts.Ipv6) {
                    boolean ipv6 = this.hostService.isIPv6(hvo.getIpAddress());
                    if (!ipv6) {
                        json.put("code", -1);
                        json.put("mes", "ip地址不合法");
                    }else{
                        this.allService.insertHostLog(list, hvo, svo);
                        /*生成hosts文件*/
                        List<groupVo> listGroup = this.groupService.getAllGroup();
                        boolean create=this.etcHostService.createHost(listGroup);
                        if(create){
                            log.info("服务重启开始了");
                            this.etcHostService.LinuxExe();
                            log.info("服务重启结束");
                        }
                    }
                }
            /*return "redirect:/showAnalysis?groupId="+hvo.getGroupId();*/
        }
        out.print(json);
        out.flush();
        out.close();
    }
    /*
    * 是否开启
    * */
    @RequestMapping(value = "/isOpen",method = RequestMethod.POST)
    public void openClose(HttpServletRequest request, HttpServletResponse response, hostsVo vo) throws Exception {
        PrintWriter out=response.getWriter();
        JSONObject json=new JSONObject();
        json.put("code",-1);
        hostsVo hvo=this.hostService.getHostById(vo);
        Date date=new Date();

        vo.setModUserId("111");
        vo.setModUserName("ooo");
        vo.setModDate(date);
        try {
            sysLogVo svo =this.logService.message("hosts",vo.getHostsId(),vo.getModUserId());
            int logId=this.allService.getNextVal("sn_log");
            svo.setLogId(logId);
            if(hvo.getState() == AllConsts.stateOpen){
                vo.setState(AllConsts.stateClose);
                this.hostService.isOpen(vo);
                svo.setLogDetail(vo.getModUserName()+"修改解析["+hvo.getIpAddress()+"]["+hvo.getHostNames()+"],原状态[启用]新状态[禁用]");
            }else{
                vo.setState(AllConsts.stateOpen);
                this.hostService.isOpen(vo);
                svo.setLogDetail(vo.getModUserName()+"修改解析["+hvo.getIpAddress()+"]["+hvo.getHostNames()+"],原状态[禁用]新状态[启用]");
            }
            this.logService.insertLog(svo);
            /*生成hosts文件*/
            List<groupVo> listGroup = this.groupService.getAllGroup();
            boolean create=this.etcHostService.createHost(listGroup);
            if(create){
                log.info("服务重启开始了");
                this.etcHostService.LinuxExe();
                log.info("服务重启结束");
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
     * 删除解析
     * */
    @RequestMapping(value = "/delHost",method = RequestMethod.POST)
    public void delHost(HttpServletRequest request, HttpServletResponse response, hostsVo vo) throws Exception {
        PrintWriter out=response.getWriter();
        JSONObject json=new JSONObject();
        json.put("code",-1);
        hostsVo hvo=this.hostService.getHostById(vo);
        vo.setModUserId("111");
        vo.setModUserName("ooo");
        vo.setModDate(new Date());
        sysLogVo svo=this.logService.message("hosts",vo.getHostsId(),vo.getModUserId());
        int logId=this.allService.getNextVal("sn_log");
        svo.setLogId(logId);
        try {
            this.hostService.delHost(vo);
            json.put("code",1);
            svo.setLogDetail(vo.getModUserName()+"删除解析["+hvo.getIpAddress()+"]["+hvo.getHostNames()+"]");
            this.logService.insertLog(svo);
            /*生成hosts文件*/
            List<groupVo> listGroup = this.groupService.getAllGroup();
            boolean create=this.etcHostService.createHost(listGroup);
            if(create){
                log.info("服务重启开始了");
                this.etcHostService.LinuxExe();
                log.info("服务重启结束");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.print(json);
        out.flush();
        out.close();
    }
    /*修改要显示的数据*/
    @RequestMapping(value = "/revise",method = RequestMethod.POST)
    public void getReviseHost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        JSONObject json=new JSONObject();
        PrintWriter out=response.getWriter();
        hostsVo hvo=new hostsVo();
        String hid=request.getParameter("hid");
        hostsVo hvo1=new hostsVo();
        hvo1.setHostsId(Integer.valueOf(hid));
        if(hid == null){

        }else{
            hvo=this.hostService.getHostById(hvo1);
            json.put("hvo",hvo);
        }
        out.print(json);
        out.flush();
        out.close();
    }

    /*
     * 修改解析
     * */
    @RequestMapping(value = "/hostModify",method = RequestMethod.POST)
    public void modifyHost(HttpServletRequest request, HttpServletResponse response, hostsVo hvo) throws Exception {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        json.put("code", 1);
        hostsVo vo = this.hostService.getHostById(hvo);
        if (StringUtils.isEmpty(hvo.getIpAddress())) {
            json.put("code", -1);
            json.put("mes", "请输入IP地址");
        } else if (StringUtils.isEmpty(hvo.getHostNames())) {
            json.put("code", -1);
            json.put("mes", "请输入解析域名");
        } else if (StringUtils.isEmpty(hvo.getMemo())) {
            json.put("code", -1);
            json.put("mes", "请输入备注");
        } else {
            /*准备数据开始*/
            hvo.setModUserId("111");
            hvo.setModUserName("ooo");
            hvo.setModDate(new Date());
            String memo = hvo.getMemo().replace("\n", " ");
            hvo.setMemo(memo);
            String host = hvo.getHostNames().replace("\n", " ");
            String[] hostNames = host.split(" ");
            List<String> list = new ArrayList<>();
            for (String str : hostNames) {
                if (!str.equals("")) {
                   hvo.setHostNames(str);
                }
            }

            sysLogVo svo = this.logService.message("hosts", hvo.getHostsId(), hvo.getModUserId());
            if (vo.getState() == AllConsts.stateOpen) {
                if (hvo.getState() == AllConsts.stateOpen) {
                    svo.setLogDetail(hvo.getModUserName() + "修改解析[原" + vo.getIpAddress() + "][原" + vo.getHostNames() + "]为[新" + hvo.getIpAddress() + "]" +
                            "[新" + hvo.getHostNames() + "],原状态[启用]新状态[启用],备注[" + hvo.getMemo() + "]");
                } else {
                    svo.setLogDetail(hvo.getModUserName() + "修改解析[原" + vo.getIpAddress() + "][原" + vo.getHostNames() + "]为[新" + hvo.getIpAddress() + "]" +
                            "[新" + hvo.getHostNames() + "],原状态[启用]新状态[禁用],备注[" + hvo.getMemo() + "]");
                }

            } else {
                if (hvo.getState() == AllConsts.stateOpen) {
                    svo.setLogDetail(hvo.getModUserName() + "修改解析[原" + vo.getIpAddress() + "][原" + vo.getHostNames() + "]为[新" + hvo.getIpAddress() + "]" +
                            "[新" + hvo.getHostNames() + "],原状态[禁用]新状态[启用],备注[" + hvo.getMemo() + "]");
                } else {
                    svo.setLogDetail(hvo.getModUserName() + "修改解析[原" + vo.getIpAddress() + "][原" + vo.getHostNames() + "]为[新" + hvo.getIpAddress() + "]" +
                            "[新" + hvo.getHostNames() + "],原状态[禁用]新状态[禁用],备注[" + hvo.getMemo() + "]");
                }

            }
            /*结束*/

            List<hostsVo> listHosts=this.hostService.getAllHosts(hvo.getGroupId());
            boolean isExist = this.hostService.isExist(listHosts, hvo.getIpAddress(), hvo.getHostNames());
            if (isExist) {
                json.put("code", -1);
                json.put("mes", hvo.getIpAddress() + "-" + hvo.getHostNames() + "已存在");
                out.print(json);
                out.flush();
                out.close();
                return;
            }

                if (hvo.getIpVersion() == AllConsts.Ipv4) {
                    boolean ipv4 = this.hostService.isIPv4(hvo.getIpAddress());
                    if (!ipv4) {
                        json.put("code", -1);
                        json.put("mes", "ip地址不合法");
                    } else {
                        this.allService.modifyHostLog(hvo, svo);
                        /*生成hosts文件*/
                        List<groupVo> listGroup = this.groupService.getAllGroup();
                        boolean create=this.etcHostService.createHost(listGroup);
                        if(create){
                            log.info("服务重启开始了");
                            this.etcHostService.LinuxExe();
                            log.info("服务重启结束");
                        }
                    }
                } else {
                    boolean ipv6 = this.hostService.isIPv6(hvo.getIpAddress());
                    if (!ipv6) {
                        json.put("code", -1);
                        json.put("mes", "ip地址不合法");
                    }else{
                        this.allService.modifyHostLog(hvo, svo);
                        /*生成hosts文件*/
                        List<groupVo> listGroup = this.groupService.getAllGroup();
                        boolean create=this.etcHostService.createHost(listGroup);
                        if(create){
                            log.info("服务重启开始了");
                            this.etcHostService.LinuxExe();
                            log.info("服务重启结束");
                        }
                    }
                }
            }
        out.print(json);
        out.flush();
        out.close();

        /*return "redirect:/showAnalysis?groupId="+vo.getGroupId();*/
    }

    /*
    * 显示导入的数据
    * */
    @RequestMapping(value = "/importData",method = RequestMethod.POST)
    public void importData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        JSONObject json=new JSONObject();
        PrintWriter out=response.getWriter();
        List<?> list=this.etcHostService.getHostsData();
        List<String> listData=new ArrayList<>();
        for(Object obj:list){
            if(obj.toString().startsWith("#")){
                String ipHost=obj.toString().replace("#","");
                /*因为ip host中间有空格，所以通过分割空格进行去除备注*/
                String[] arrays=ipHost.split(" ");
                if(arrays.length == 2){
                    listData.add(obj.toString() + "\n");
                }
            }else {
                if(StringUtils.isEmpty(obj.toString())){

                }else {
                    listData.add(obj + "\n");
                }
            }
        }
        json.put("ss",listData);
        out.print(json);
        out.flush();
        out.close();
    }

    /*保存导入的数据*/
    @RequestMapping(value = "/saveImport",method = RequestMethod.POST)
    public void saveImport(HttpServletResponse response,HttpServletRequest request,hostsVo hvo) throws Exception {
        response.setContentType("text/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        JSONObject json=new JSONObject();
        json.put("code",1);
        PrintWriter out=response.getWriter();
        String[] arrays=hvo.getIpHost().split("\n");
        hostsVo hostsVo=null;
        String remove= null;
        List<hostsVo> list=new ArrayList<>();
        for(String str:arrays){
            if(!StringUtils.isEmpty(str)){
                hostsVo=new hostsVo();
                /*准备数据开始*/
                if(str.startsWith("#")){
                    hostsVo.setState(AllConsts.stateClose);
                    remove=str.replace("#","");
                }else{
                    hostsVo.setState(AllConsts.stateOpen);
                    remove = str;
                }
                String[] ipHost=remove.split(" ");
                String ip=ipHost[0];
                String hostName=ipHost[1];

                hostsVo.setIpAddress(ip);
                hostsVo.setHostNames(hostName);
                hostsVo.setMemo(hvo.getMemo());
                hostsVo.setGroupId(hvo.getGroupId());

                hostsVo.setModUserId("111");
                hostsVo.setModUserName("ddd");
                hostsVo.setModDate(new Date());
                /*准备的一部分数据结束*/
                boolean isIPV4=this.hostService.isIPv4(ip);
                boolean isIPV6=this.hostService.isIPv6(ip);
                List<hostsVo> listHosts=this.hostService.getAllHosts(hvo.getGroupId());
                boolean isExist = this.hostService.isExist(listHosts,ip,hostName);
                if(StringUtils.isEmpty(hvo.getMemo())){
                    json.put("code",-1);
                    json.put("mes","请填写备注");
                    out.print(json);
                    out.flush();
                    out.close();
                    return;
                } else if(isIPV4){
                    if(isExist){
                        json.put("code",-1);
                        json.put("mes",ip + "-" + hostName + "已存在");
                        out.print(json);
                        out.flush();
                        out.close();
                        return;
                    }else{
                        /*在这里面写 是因为存在的数据也会生成 hostId和listOrder*/
                        /*写公共方法  前面准备的数据没有了*/
                        int hostId = this.allService.getNextVal("sn_hosts");
                        int listOrder=this.allService.getNextVal("sn_hostListOrder");
                        hostsVo.setHostsId(hostId);
                        hostsVo.setListOrder(listOrder);
                        hostsVo.setIpVersion(AllConsts.Ipv4);
                        list.add(hostsVo);
                    }
                }else if(isIPV6){
                    if(isExist){
                        json.put("code",-1);
                        json.put("mes",ip + "-" + hostName + "已存在");
                        out.print(json);
                        out.flush();
                        out.close();
                        return;
                    }else{
                        int hostId = this.allService.getNextVal("sn_hosts");
                        int listOrder=this.allService.getNextVal("sn_hostListOrder");
                        hostsVo.setHostsId(hostId);
                        hostsVo.setListOrder(listOrder);
                        hostsVo.setIpVersion(AllConsts.Ipv6);
                        list.add(hostsVo);
                    }
                }

            }


        }
        for(hostsVo vo:list){
            this.allService.insertHostLog(vo);
        }
        List<groupVo> listGroup = this.groupService.getAllGroup();
        boolean create=this.etcHostService.createHost(listGroup);
        if(create){
            log.info("服务重启开始了");
            this.etcHostService.LinuxExe();
            log.info("服务重启结束");
        }
        out.print(json);
        out.flush();
        out.close();


    }

}
