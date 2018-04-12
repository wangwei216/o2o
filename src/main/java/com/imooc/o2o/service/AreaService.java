package com.imooc.o2o.service;

import com.imooc.o2o.entity.Area;

import java.util.List;

public interface AreaService {

    //这个是service层要通过DAO层要实现的接口
    List<Area> getAreaList();
}
