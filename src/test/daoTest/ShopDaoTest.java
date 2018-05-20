package daoTest;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
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
public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;

	@Test
	@Ignore
	public void testAInsertShop() throws Exception {
		Shop shop = new Shop();
		shop.setOwnerId(66);
		Area area = new Area();
		area.setAreaId(66);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(66);
		shop.setShopName("mytest1");
		shop.setShopDesc("mytest1");
		shop.setShopAddr("testaddr1");
		shop.setPhone("13810524526");
		shop.setShopImg("test1");
		shop.setLongitude(1D);
		shop.setLatitude(1D);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("审核中");
		shop.setArea(area);
		shop.setShopCategory(sc);
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}

	/*@Test
	@Ignore
	public void testBQueryByEmployeeId() throws Exception {
		long employeeId = 1;
		List<Shop> shopList = shopDao.queryByEmployeeId(employeeId);
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}*/

	/*
	* 这个是测试拿到shop的所有数据的总数的列表
	* */
	//TODO 通过测试
	@Test
	public void testBQueryShopList() throws Exception {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(8);
		shopCondition.setPersonInfo(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 10);
		/*assertEquals(10,shopList.size());*/
		int shopCount = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小"+ shopList.size());
		System.out.println("店铺的总数"+shopCount);

	}

	@Test
	@Ignore
	public void testQueryShopCount() throws Exception{
		Shop shopCondition = new Shop();
		PersonInfo personInfo = new PersonInfo();
		shopCondition.setPersonInfo(personInfo);
		int queryShopCount = shopDao.queryShopCount(shopCondition);
		assertEquals(queryShopCount,10);
	}

	@Test
	@Ignore
	public void testCQueryByShopId() throws Exception {
		long shopId = 15;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("从shop实体中获取区域实体信息中的名字"+shop.getArea().getAreaName());
		System.out.println("从shop实体中获取区域实体信息中的id"+ shop.getArea().getAreaId());
	}

	@Test
	@Ignore
	public void testDUpdateShop() {
		long shopId = 1;
		Shop shop = shopDao.queryByShopId(shopId);
		Area area = new Area();
		area.setAreaId(25);
		shop.setArea(area);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(64);
		shop.setShopCategory(shopCategory);
		shop.setShopName("四季花");
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}

	/*@Test
	public void testEDeleteShopByName() throws Exception {
		String shopName = "mytest1";
		int effectedNum = shopDao.deleteShopByName(shopName);
		assertEquals(1, effectedNum);

	}*/

}
