package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;

/*这个是对商品信息的要实现的功能接口*/
public interface ProductDao {

    //这个是插入商品信息
    int insertProduct(Product product);
}
