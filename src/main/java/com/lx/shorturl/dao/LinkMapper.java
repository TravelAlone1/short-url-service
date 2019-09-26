package com.lx.shorturl.dao;

import com.lx.shorturl.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
*@Author: lx
*@Date: 2019/9/25 16:07
*/
@Mapper
public interface LinkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Link record);

    int insertSelective(Link record);

    Link selectByPrimaryKey(Integer id);



    Link findByShortCode(String shortCode);

    int updateByPrimaryKeySelective(Link record);

    int updateByPrimaryKey(Link record);
}