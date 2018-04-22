package daoTest;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.test.BaseTest;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
	@Autowired
	private ProductDao productDao;

	/*@Test
	@Ignore
	public void testABatchInsertProductImg() throws Exception {
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
	}*/


/*
	@Test
	@Ignore
	public void testCDeleteProductImgByProductId() throws Exception {
		long productId = 10;
		int effectedNum = productDao.deleteProductImgByProductId(productId);
		assertEquals(3, effectedNum);
	}*/

}
