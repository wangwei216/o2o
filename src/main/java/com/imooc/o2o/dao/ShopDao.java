package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    //这个是实现对商铺的增加
    int insertShop(Shop shop);
    //这个实现的是对商铺的修改
    int updateShop(Shop shop);
    //这个实现的查询的接口
    Shop queryByShopId(long shopId);

    //分页查询店铺信息，需要实现分页功能，
    // 传进来的第二个参数就是从第几行开始，
    // 第三个参数就是返回多少行
    //TODO 通过测试
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
                             @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    //返回商铺的全部总条数，这个是为了分页的时候需要查询数据的总数来进行判断分多少页
    //TODO 通过测试
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
