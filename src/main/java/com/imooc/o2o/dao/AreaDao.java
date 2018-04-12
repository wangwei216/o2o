package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Area;

import java.util.List;

//这个是要实现区域实体功能的DAO接口
public interface AreaDao {

    //这个是列出区域列表
    List<Area> queryArea();


}
