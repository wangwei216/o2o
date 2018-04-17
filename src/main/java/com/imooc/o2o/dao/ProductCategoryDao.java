package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;

import java.util.List;

/*
    * 这个是对商品功能的DAO接口进行实现的功能
    *
    * */
public interface ProductCategoryDao {

    //通过查询商铺id来查询所有的商品信息
    List<ProductCategory> queryProductCategoryList(Long shopId);
}
