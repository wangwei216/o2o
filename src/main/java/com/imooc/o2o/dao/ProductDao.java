package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;

/*这个是对商品信息的要实现的功能接口*/
public interface ProductDao {

    //这个是插入商品信息
    int insertProduct(Product product);

    //通过商品id productId来查询唯一的商品信息，返回的是商品类型信息
    Product queryProductById(long productId);
    //根据传入的商品实体类去修改商品信息，返回的是int
    int updateProduct(Product product);
    //通过商品信息的id来去删除商品的详情信息
    int deleteProductImgByProductId(long productId);

}
