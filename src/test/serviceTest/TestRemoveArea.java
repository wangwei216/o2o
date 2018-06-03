package serviceTest;

import com.imooc.o2o.dto.AreaExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.enums.AreaStateEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestRemoveArea extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void TestRemoveAreaTest(){

        Area area = new Area();
        area.setAreaId(9);

         AreaExecution sum =areaService.removeArea(area.getAreaId());
         if (sum.getState()== AreaStateEnum.SUCCESS.getState()){
             System.out.println("说明成功了");
         }


    }

}
