package com.imooc.o2o.controller;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

    //定义一个logback
    org.slf4j.Logger logger =LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listArea(){
        logger.info("===start====");
        Map<String,Object> modelAndMap = new HashMap<String, Object>();
        List<Area> list = new ArrayList<Area>();

       try {
           list = areaService.getAreaList();
           modelAndMap.put("rows",list);
           modelAndMap.put("total",list.size());
       }catch (Exception e){
           e.printStackTrace();
           modelAndMap.put("success",false);
           modelAndMap.put("errMsg",e.toString());
       }

        return modelAndMap;
    }

}
