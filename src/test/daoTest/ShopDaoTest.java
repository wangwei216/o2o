package daoTest;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.test.BaseTest;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;



	@Test
	public void testBQueryShopList() throws Exception {
		Shop shop = new Shop();
		List<Shop> shopList = shopDao.queryShopList(shop, 0, 2);
		assertEquals(2, shopList.size());
		int count = shopDao.queryShopCount(shop);
		assertEquals(3, count);
		shop.setShopName("花");
		shopList = shopDao.queryShopList(shop, 0, 3);
		assertEquals(2, shopList.size());
		count = shopDao.queryShopCount(shop);
		assertEquals(2, count);
		shop.setShopId(1L);
		shopList = shopDao.queryShopList(shop, 0, 3);
		assertEquals(1, shopList.size());
		count = shopDao.queryShopCount(shop);
		assertEquals(1, count);

	}

	@Test
	@Ignore
	public void testCQueryByShopId() throws Exception {
		long shopId = 1;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println(shop);
	}


}
