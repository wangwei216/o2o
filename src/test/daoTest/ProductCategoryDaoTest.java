package daoTest;


import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.test.BaseTest;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
		@Ignore
	public void testAInsertProductCategory() throws Exception {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(15);

			ProductCategory productCategory2 = new ProductCategory();
			productCategory2.setProductCategoryName("商品类别2");
			productCategory2.setPriority(3);
			productCategory2.setCreateTime(new Date());
			productCategory2.setShopId(15);

		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		int effectedNum = productCategoryDao
				.batchInsertProductCategory(productCategoryList);
		assertEquals(2, effectedNum);
	}

	@Test
	@Ignore
	//TODO  这个版块已经完成
	public void testBQueryByShopId() throws Exception {
		long shopId = 20;
		List<ProductCategory> productCategoryList = productCategoryDao
				.queryProductCategoryList(shopId);
		assertEquals(5, productCategoryList.size());


	}

	@Test
	public void testCDeleteProductCategory() throws Exception {
		long shopId = 15;
		long productCategoryId = 15;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println(productCategoryList.size());
		int effectedNum = productCategoryDao.deleteProductCategory(
				productCategoryList.get(0).getProductCategoryId(), shopId);
		assertEquals(1, effectedNum);

	}

}
