package com.imooc.myo2o.dao;

import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends com.imooc.o2o.test.BaseTest {

	@Autowired
	private ShopCategoryDao shopCategoryDao;



	@Test
	public void testBQueryShopCategory() throws Exception {
		ShopCategory sc = new ShopCategory();
		List<ShopCategory> shopCategoryList = shopCategoryDao
				.queryShopCategory(sc);
		assertEquals(3, shopCategoryList.size());
		sc.setParentId(1L);
		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(1, shopCategoryList.size());
		sc.setParentId(null);
		sc.setShopCategoryId(0L);
		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(2, shopCategoryList.size());

	}


}
