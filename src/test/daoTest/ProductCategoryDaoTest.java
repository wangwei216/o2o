package daoTest;


import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.test.BaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
	@Autowired
	private ProductCategoryDao productCategoryDao;



		@Test
	public void testAInsertProductCategory() throws Exception {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(1L);

			ProductCategory productCategory2 = new ProductCategory();
			productCategory2.setProductCategoryName("商品类别2");
			productCategory2.setPriority(3);
			productCategory2.setCreateTime(new Date());
			productCategory2.setShopId(2L);

		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		int effectedNum = productCategoryDao
				.batchInsertProductCategory(productCategoryList);
		assertEquals(2, effectedNum);
	}

/*	@Test
	public void testBQueryByShopId() throws Exception {
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryDao
				.queryByShopId(shopId);
		assertEquals(2, productCategoryList.size());
		System.out.println(productCategoryList.get(0).toString());

	}

	@Test
	public void testCDeleteProductCategory() throws Exception {
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryDao
				.queryByShopId(shopId);
		int effectedNum = productCategoryDao.deleteProductCategory(
				productCategoryList.get(0).getProductCategoryId(), shopId);
		assertEquals(1, effectedNum);
		effectedNum = productCategoryDao.deleteProductCategory(
				productCategoryList.get(1).getProductCategoryId(), shopId);
		assertEquals(1, effectedNum);
	}*/
}
