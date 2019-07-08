package com.enorth.dns.dnshosts.serviceImpl;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/18
 * */


import com.enorth.dns.dnshosts.dao.groupDao;
import com.enorth.dns.dnshosts.service.GroupService;
import com.enorth.dns.dnshosts.vo.Page;
import com.enorth.dns.dnshosts.vo.groupVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger log= LoggerFactory.getLogger(GroupServiceImpl.class);
    @Autowired
    private groupDao groupDao;

    /*添加*/
    @Override
    public void insertGroup(groupVo groupVo) throws Exception {
            groupDao.insertGroup(groupVo);
    }

    /*查询所有*/
    @Override
    public List<groupVo> getAllGroup() throws Exception {
        return groupDao.getAllGroup();
    }
    /*
    * 分页
    * */
    public Page<groupVo> getGroups(Page<groupVo> page){
        Page<groupVo> pages = new Page<>();
        List<groupVo> list = this.groupDao.getGroups(page);
        pages.setResults(list);
        return pages;
    }

    /*删除*/
    @Override
    public void delGroup(groupVo vo) throws Exception {
        this.groupDao.delGroup(vo);
    }

    /*通过id查询数据*/
    @Override
    public groupVo getGroupById(int id) throws Exception {
        groupVo gVo=new groupVo();
        gVo=this.groupDao.getGroupById(id);
        return gVo;
    }

    /*是否开启*/
    @Override
    public void updateGroup(groupVo vo) throws Exception {
        this.groupDao.updateGroup(vo);
    }

    /*修改*/
    @Override
    public void modifyGroup(groupVo groupVo) throws Exception {
        this.groupDao.modifyGroup(groupVo);
    }

    /*模糊查询*/
    @Override
    public List<groupVo> getLikeGroup(groupVo groupVo) throws Exception {
        List<com.enorth.dns.dnshosts.vo.groupVo> list=new ArrayList<>();
        list=this.groupDao.getLikeGroup(groupVo);
        return list;
    }
}
