package com.imooc.o2o.service.serviceImpl;

import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.dto.AreaExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.enums.AreaStateEnum;
import com.imooc.o2o.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaDao areaDao;
   /* @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;

    private static String AREALISTKEY;*/
    @Override
    public List<Area> getAreaList() {
       /* String key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();

        if (jedisKeys.exists(key)){
            areaList = areaDao.queryArea();
            String jsonString;
            //然后把areaList转化为json
            try {
                 jsonString = mapper.writeValueAsString(areaList);
                jedisStrings.set(key,jsonString);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedisStrings.set(key,jsonString);
        }
        else {
            String jsonString = jedisStrings.get(key);

        }*/
        return areaDao.queryArea();
    }

    /*
    * 添加区域信息
    *   1.先看我传进来的是不是为空
    * */
    @Override
    public AreaExecution addArea(Area area) {
        if (area.getAreaName()!=null && !"".equals(area.getAreaName())){
            area.setCreateTime(new Date());
            area.setLastEditTime(new Date());
            int effectNum = areaDao.insertArea(area);
            //再去判断是不是影响了数据库
            if (effectNum>0){
                return new AreaExecution(AreaStateEnum.SUCCESS);
            }
            else {
                return new AreaExecution(AreaStateEnum.INNER_ERROR);
            }
        }
        else {
            return new AreaExecution(AreaStateEnum.EMPTY);
        }
    }

    @Override
    public AreaExecution modifyArea(Area area) {
        return null;
    }

    @Override
    public AreaExecution removeArea(long areaId) {
        return null;
    }

    @Override
    public AreaExecution removeAreaList(List<Long> areaIdList) {
        return null;
    }


}
