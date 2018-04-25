package com.imooc.o2o.dao;


import com.imooc.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
        *
        * 这个就是对头条的展示
        * */
public interface HeadLineDao {

    /*
    * 根据头条传入的查询条件
    * */
    List<HeadLine> queryHeadLine(@Param("headLineCondition")HeadLine headLineCondition);

    /**/
}
