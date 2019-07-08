package com.enorth.dns.dnshosts.dao;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/19
 * */


import com.enorth.dns.dnshosts.vo.hostsVo;

import java.util.List;

public interface hostDao {
    void insertHost(hostsVo hostsVo);
    List<hostsVo> getAllhosts(int groupId);
    void isOpen(hostsVo vo);
    hostsVo getHostById(hostsVo vo);
    void delHost(hostsVo vo);
    void modifyHost(hostsVo vo);
    List<hostsVo> getLikeHost(hostsVo vo);
}
