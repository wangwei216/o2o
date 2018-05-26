package daoTest;


import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.test.BaseTest;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;


	/*这个是测试添加商品信息，因为商品信息是在shopId下的 而且商品信息还会分为不同的商品分类下面也就是shopCategoryId*/
	@Test
	@Ignore
	public void testAInsertProduct() throws Exception {
		Shop shop1 = new Shop();
		shop1.setShopId(15);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(9);
		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试Desc1");
		product1.setImgAddr("test1");
		product1.setPriority(0);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1, effectedNum);
	}

	/*这个测试的是查询商品列表，并且实现的是分页的效果*/
	@Test
	public void testBQueryProductList() throws Exception {
		Product productConditon = new Product();

		/*List<Product> queryProductList = productDao.queryProductList(productConditon, 0, 30);
		System.out.println(queryProductList.size());*/
		//如果使用模糊查询 看看能不能能查询到数据
		productConditon.setProductName("奶茶");
		List<Product> productList = productDao.queryProductList(productConditon, 0, 30);
		System.out.println(productList.size());


	}

	/*这个是测试查询*/
	@Test
	@Ignore
	public void testCQueryProductByProductId() throws Exception {


	}

	@Test
	@Ignore
	public void testDUpdateProduct() throws Exception {
		Product product = new Product();
		product.setProductId(10);
		product.setProductName("第一个产品");
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1, effectedNum);
	}


}
