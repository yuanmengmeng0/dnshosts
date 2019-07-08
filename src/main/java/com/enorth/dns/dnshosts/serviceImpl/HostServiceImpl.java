package com.enorth.dns.dnshosts.serviceImpl;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/19
 * */


import com.enorth.dns.dnshosts.dao.hostDao;
import com.enorth.dns.dnshosts.service.HostService;
import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.hostsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Transactional
@Service
public class HostServiceImpl implements HostService {
    private static final Logger log= LoggerFactory.getLogger(HostServiceImpl.class);
    @Autowired
    private hostDao hostDao;

    /*
    * 添加
    * */
    @Override
    public void insertHost(hostsVo hostsVo) throws Exception {
        this.hostDao.insertHost(hostsVo);
    }

    /*
    * 获取所有
    * */
    @Override
    public List<hostsVo> getAllHosts(int groupId) throws Exception {
        List<hostsVo> list=new ArrayList<>();
        list=this.hostDao.getAllhosts(groupId);
        return list;
    }
    /*分页查询*/
    @Override
    public Page<hostsVo> getAllHost(Page<hostsVo> page,int groupId) throws Exception {
        Page<hostsVo> pages=new Page<>();
        List<hostsVo> list=new ArrayList<>();
        Map<String,Object> map= new HashMap<>();
        map.put("startData",page.getStartData());
        map.put("pageSize",page.getPageSize());
        map.put("groupId",groupId);
        list=this.hostDao.getAllhost(map);
        pages.setPageNo(page.getPageNo());
        pages.setResults(list);
        pages.setTotalRecord(this.getAllHosts(groupId).size());
        return pages;
    }

    /*
    * 禁用/启用
    * */
    @Override
    public void isOpen(hostsVo vo) throws Exception {
        this.hostDao.isOpen(vo);
    }

    /*
    * id查询
    * */
    @Override
    public hostsVo getHostById(hostsVo vo) throws Exception {
        hostsVo hvo=new hostsVo();
        hvo=this.hostDao.getHostById(vo);
        return hvo;
    }

    /*
    * 删除
    * */
    @Override
    public void delHost(hostsVo vo) throws Exception {
        this.hostDao.delHost(vo);
    }

    /*
    * 修改
    * */
    @Override
    public void modifyHost(hostsVo vo) throws Exception {
        this.hostDao.modifyHost(vo);
    }

    @Override
    public List<hostsVo> getLikeHost(hostsVo vo) throws Exception {
        List<hostsVo> list=new ArrayList<>();
        list=this.hostDao.getLikeHost(vo);
        return list;
    }
    @Override
    public Page<hostsVo> getLikeHosts(hostsVo vo,Page<hostsVo> page) throws Exception {
        Page<hostsVo> pages = new Page<>();
        Map<String,Object> map=new HashMap<>();
        List<hostsVo> list=new ArrayList<>();
        map.put("groupId",vo.getGroupId());
        map.put("state",-1);
        map.put("ipAddress",vo.getIpAddress());
        map.put("hostNames",vo.getHostNames());
        map.put("startData",page.getStartData());
        map.put("pageSize",page.getPageSize());
        list=this.hostDao.getLikeHosts(map);
        pages.setResults(list);
        pages.setPageNo(page.getPageNo());
        pages.setTotalRecord(this.getLikeHost(vo).size());
        return pages;
    }

    /*验证IPV4的ip合法性*/
    public boolean isIPv4(String str) {
        if (!Pattern.matches("[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+", str)) {
            return false;
        }
        else {
            String[] arrays = str.split("\\.");//  这里的 \\ 不能少
            if (Integer.parseInt(arrays[0]) < 256 && arrays[0].length() <= 3
                    && Integer.parseInt(arrays[1]) < 256 && arrays[1].length() <= 3
                    && Integer.parseInt(arrays[2]) < 256 && arrays[2].length() <= 3
                    && Integer.parseInt(arrays[3]) < 256 && arrays[3].length() <= 3){
                return true;
        }else{
            return false;
        }
        }
    }


    /*验证IPV6的IP合法性*/
    public boolean isIPv6(String str) {
        return isIPV6Std(str) || isIPV6Compress(str);
    }

    public boolean isIPV6Std(String str) {
        if (!Pattern.matches("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$", str)) {
            return false;
        }
        return true;
    }

    public boolean isIPV6Compress(String str) {
        if (!Pattern.matches(
                "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$", str)) {
            return false;
        }
        return true;
    }

    /*检验ip-域名规则与现有是否冲突*/
    public boolean isExist(List<hostsVo> listHost, String ipAddress, String hostName){
        boolean flag = false;
        for(hostsVo vo:listHost){
            boolean address=vo.getIpAddress().equals(ipAddress);
            boolean name= vo.getHostNames().equals(hostName);
            if( address && name){
                flag = true;
            }
        }
        return flag;
    }

}
