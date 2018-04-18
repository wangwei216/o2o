package com.imooc.o2o.dao;


import com.imooc.o2o.entity.ProductImg;

import java.util.List;
//这个是对商品图片信息功能的去进行实现的功能接口
public interface ProductImgDao {

    //这个是查询商品图片信息列表
    List<ProductImg> queryProductList(long productId);

    //这个是批量进行插入对商品详情的图片信息
    int batchInsertProductImg(List<ProductImg> productImgList);

    //通过商品信息id去删除商品图片信息
    int deleteProductImgByProductId(long productId);
}
