package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
    * 这个是对商品功能的DAO接口进行实现的功能
    *
    * */
public interface ProductCategoryDao {

    //通过查询商铺id来查询所有的商品信息
    List<ProductCategory> queryProductCategoryList(Long shopId);
    //批量插入商品信息的功能
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    //删除商品类别（初版，即只支持删除尚且没有发布商品的商品类别）
    int deleteProductCategory(
            @Param("productCategoryId") long productCategoryId,
            @Param("shopId") long shopId);
}
