package com.enorth.dns.dnshosts.service;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */



import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.groupVo;

import java.util.List;

public interface GroupService {
    void insertGroup(groupVo groupVo) throws Exception;
    List<groupVo> getAllGroup() throws Exception;
    void delGroup(groupVo vo) throws Exception;
    groupVo getGroupById(int id) throws Exception;
    void updateGroup(groupVo vo)throws  Exception;
    void modifyGroup(groupVo groupVo)throws  Exception;
    List<groupVo> getLikeGroup(groupVo groupVo) throws Exception;
    Page<groupVo> getLikeGroups(groupVo vo,Page<groupVo> page) throws Exception;
}
