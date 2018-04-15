package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {

    //这个是实现对上铺的增
    int insertShop(Shop shop);
    //这个实现的是对商铺的修改
    int updateShop(Shop shop);

}
