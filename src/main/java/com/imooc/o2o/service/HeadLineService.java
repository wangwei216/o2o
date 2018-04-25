package com.imooc.o2o.service;

import com.imooc.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/*
    *
    * 这个是首页头条的
    * */
public interface HeadLineService {

    /*
    * 根据传入的查询条件返回指定的头条列表
    *   1.首先你要先考虑你返回的结果是什么类型
    *   2.传进来的参数是什么（这个一般都是和Dao层的参数保持一致）
    *   3.命名怎么去命名
    * */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

}
