package com.enorth.dns.dnshosts.dao;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/21
 * */

import org.apache.ibatis.annotations.Param;

public interface CommonDao {
    int getNextVal(@Param("name") String name);
}
