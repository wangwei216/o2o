package com.imooc.o2o.service;

import com.imooc.o2o.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    //这个是通过商品id来得到该商铺下商品的所有信息
    List<ProductCategory> getProductCategoryList(long shopId);
}
