package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.dto.ShopOperationException;
import com.imooc.o2o.entity.Shop;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;

@Service
public interface ShopService {


	/**
	 * 创建商铺
	 * 
	 * @param Shop
	 *            shop
	 * @return ShopExecution shopExecution
	 * @throws Exception
	 */
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws RuntimeException;

	/*通过店铺id获取店铺信息*/
	Shop getByShopId(long shopId);

	/*修改店铺信息，因为修改店铺信息需要子啊提交事务的时候判断是否成功，需要用到返回的状态码*/
	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

	/*根据shopCondition分页返回响应的数据*/
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

}
