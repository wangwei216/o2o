package daoTest;

import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
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
public class AreaDaoTest extends BaseTest {
	@Autowired
	private AreaDao areaDao;

	@Test
	@Ignore
	public void testAInsertArea() throws Exception {
		Area area = new Area();
		area.setAreaName("区域1");
		area.setAreaDesc("区域1");
		area.setPriority(1);
		area.setCreateTime(new Date());
		area.setLastEditTime(new Date());
		int effectedNum = areaDao.insertArea(area);
		assertEquals(1, effectedNum);
	}

	@Test
	@Ignore
	public void testBQueryArea() throws Exception {
		List<Area> areaList = areaDao.queryArea();
		assertEquals(4, areaList.size());
	}

	@Test
	@Ignore
	public void testCUpdateArea() throws Exception {
		Area area = new Area();
		area.setAreaId(7);
		area.setAreaName("王伟");
		area.setAreaDesc("这个是区域信息描述");
		area.setLastEditTime(new Date());
		int effectedNum = areaDao.updateArea(area);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testDDeleteArea() throws Exception {
		int  areaId = 7;
		int result = areaDao.deleteArea(areaId);
		assertEquals(1,result);

	}




}
