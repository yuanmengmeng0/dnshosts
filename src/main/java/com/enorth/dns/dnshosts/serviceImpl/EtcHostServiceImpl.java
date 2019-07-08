package com.enorth.dns.dnshosts.serviceImpl;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/20
 * */

import com.enorth.dns.dnshosts.consts.AllConsts;
import com.enorth.dns.dnshosts.vo.groupVo;
import com.enorth.dns.dnshosts.vo.hostsVo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Transactional
@Service
public class EtcHostServiceImpl {
    private static final Logger log= LoggerFactory.getLogger(EtcHostServiceImpl.class);
    ResourceBundle rb = ResourceBundle.getBundle("path");
    String etcFilName = rb.getString("etcFilName");
    String tmpFilName = rb.getString("tmpFilName");
    String timeFilName = rb.getString("timeFilName");
/*    private static String etcFilName="/etc/hosts";
    private static String tmpFilName="/tmp/hosts";
    private static String timeFilName="/timeFile/hosts";*/

    @Autowired
    private HostServiceImpl hostService;

    public List<?> getHostsData(){
        /*
         * 1.获取hosts文件信息
         * */
        List<?> hostFileDataLines = null;
        try {
            hostFileDataLines = FileUtils.readLines(new File(etcFilName), "utf-8");
        }catch (IOException e){
            System.out.println("Reading host file occurs error."+e.getMessage());
        }
        return hostFileDataLines;
    }

    /*
     * 生成hosts文件
     * */
    public synchronized  boolean createHost(List<groupVo> listGroup) throws Exception{
        /*生成hosts文件之前备份原来的*/
        /*
        * 1.获取hosts文件信息
        * */
        List<?> hostFileDataLines = null;
            hostFileDataLines = FileUtils.readLines(new File(etcFilName), "utf-8");
            /*
             * 2.生成时间戳
             * */
            String fileTime = getHostTime();
            List<String> listTime=new ArrayList<>();
            for (Object line:hostFileDataLines){
                String strLine = line.toString();
                listTime.add(strLine);
                FileUtils.writeLines(new File(fileTime),"utf-8",listTime);
            }

        create(listGroup);

        return true;
    }

    private  void create(List<groupVo> list) throws Exception {
        String splitter = " ";
        String fileName = getHostFile();
        List<String> newLineList = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        newLineList.add(new StringBuilder("#").append("本文件由dns管理程序生成,生成时间").toString().trim()+sdf.format(new Date()));
        for (groupVo vo:list){
            newLineList.add("\n"+new StringBuilder("#").append(vo.getGroupName()).toString().trim());
            List<hostsVo> listHosts=this.hostService.getAllHosts(vo.getGroupId());
            for(hostsVo host:listHosts){
                newLineList.add(new StringBuilder("#").append(host.getMemo()).toString().trim());
                if(vo.getState() == AllConsts.stateOpen && host.getState() == AllConsts.stateOpen){
                    newLineList.add(new StringBuilder(host.getIpAddress()).append(splitter).append(host.getHostNames()).toString());
                }else{
                    newLineList.add(new StringBuilder("#").append(host.getIpAddress()).append(splitter).append(host.getHostNames()).toString());
                }

            }
            FileUtils.writeLines(new File(fileName),"utf-8",newLineList);
        }
    }

    private  String getHostFile() throws IOException {
        String fileName = tmpFilName;
        /*判断系统*/
        if("linux".equalsIgnoreCase(System.getProperty("os.name"))){
            File file=new File(fileName);
            file.createNewFile();
        }else{
//            fileName=System.getenv("windir")+"/system32/drivers/etc/hosts";
            File file=new File(fileName);
            File file1=file.getParentFile();
            if(!file1.exists()){
                file1.mkdirs();
            }
            file.createNewFile();
        }
        return fileName;
    }

    /*时间戳文件*/
    private  String getHostTime() throws IOException {
        String fileName = null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            fileName=timeFilName+"."+sdf.format(new Date());
            File file=new File(fileName);
            File file1=file.getParentFile();
            if(!file1.exists()){
                file1.mkdirs();
            }
            file.createNewFile();
        return fileName;
    }


    /*
    * 执行linux命令
    * */
    public void LinuxExe() throws IOException, InterruptedException {
        Runtime.getRuntime().exec(new String[]{"sh","-c","cd /tmp && mv hosts ../etc"});
        Runtime.getRuntime().exec(new String[]{"sh","-c","service dnsmasq restart"});
    }
}
