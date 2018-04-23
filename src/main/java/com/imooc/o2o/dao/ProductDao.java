package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

	/*
	* 查询商品列表，并分页实现，可以输入的条件是商品名、商品状态、店铺ID、商品类别
	* */
	List<Product> queryProductList(@Param("productCondition")Product productCondition,
								   @Param("rowIndex")int rowIndex,		//开始查询的下标
								   @Param("pageSize") int pageSize);	//一页能装多少条数据
	/*
	* 查询对应商品的总数
	*
	* */
	int queryProductCount(@Param("productCondition") Product productCondition);




}
