package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;

public interface ProductDao {

	Product queryProductByProductId(long productId);

	/*
	* 插入商品
	* */
	int insertProduct(Product product);

	/*
	* 更新商品信息
	* */
	int updateProduct(Product product);




}
