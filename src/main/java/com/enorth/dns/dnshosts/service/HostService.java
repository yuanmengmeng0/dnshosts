package com.enorth.dns.dnshosts.service;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/19
 * */


import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.hostsVo;

import java.util.List;

public interface HostService {
    void insertHost(hostsVo hostsVo) throws Exception;
    List<hostsVo> getAllHosts(int groupId) throws Exception;
    Page<hostsVo> getAllHost(Page<hostsVo> page, int groupId) throws Exception;
    void isOpen(hostsVo vo) throws Exception;
    hostsVo getHostById(hostsVo vo) throws Exception;
    void delHost(hostsVo vo) throws Exception;
    void modifyHost(hostsVo vo) throws Exception;
    List<hostsVo> getLikeHost(hostsVo vo) throws Exception;
    Page<hostsVo> getLikeHosts(hostsVo vo,Page<hostsVo> page) throws Exception;
}

