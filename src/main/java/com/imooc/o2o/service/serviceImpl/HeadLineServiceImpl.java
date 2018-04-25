package com.imooc.o2o.service.serviceImpl;

import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.service.HeadLineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService{

    @Resource
    private HeadLineDao headLineDao;

    /*
    * 这个是实现servic层的接口，从Dao层得到HeadLine的头条信息
    * */
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {

        return headLineDao.queryHeadLine(headLineCondition);
    }
}
