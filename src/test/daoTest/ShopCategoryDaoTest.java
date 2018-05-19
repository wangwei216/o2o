package daoTest;

import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends com.imooc.o2o.test.BaseTest {

	@Autowired
	private ShopCategoryDao shopCategoryDao;



	@Test
	@Ignore
	public void testBQueryShopCategory() throws Exception {
		ShopCategory sc = new ShopCategory();
		List<ShopCategory> shopCategoryList = shopCategoryDao
				.queryShopCategory(sc);
		assertEquals(3, shopCategoryList.size());
		sc.setParentId(1);
		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(1, shopCategoryList.size());
		sc.setParentId(0);
		sc.setShopCategoryId(0);
		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(2, shopCategoryList.size());

	}

	/*这个测试的是测试Dao查询商品类别的列表*/
	@Test
	public void testQueryShopCategory(){


		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
		System.out.println(shopCategoryList.size());
		assertEquals(18,shopCategoryList.size());

	}


}
