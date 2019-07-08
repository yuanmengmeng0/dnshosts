package com.enorth.dns.dnshosts.dao;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/17
 * */


import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.groupVo;

import java.util.List;
import java.util.Map;

public interface groupDao {
    List<groupVo> getAllGroup();
    List<groupVo> getGroups(Page<groupVo> page);
    void insertGroup(groupVo groupVo);
    void delGroup(groupVo vo);
    groupVo getGroupById(int id);
    /*启用/禁用*/
    void updateGroup(groupVo vo);
    void modifyGroup(groupVo vo);
    List<groupVo> getLikeGroup(groupVo groupVo);
    List<groupVo> getLikeGroups(Map<String,Object> map);
}
