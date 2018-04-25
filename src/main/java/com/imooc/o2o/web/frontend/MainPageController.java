package com.imooc.o2o.web.frontend;


import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.HeadLineService;
import com.imooc.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/frontend")
@Controller
public class MainPageController {

    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    /*
    * 初始化前端展示系统的主页信息，包括获取一级店铺类别及头条列表
    * */
    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listMainPageInfo(){
        /*
        一、先获取商品店铺信息列表（不是店铺下的商品详情信息）
        * 1.需要先定义一个模型，来把数据放进去
        * 2.然后通过service层方法查询出一级店铺列表（也就是当shopId为空的时候，会查询到所有的店铺信息）
        * 3.把从service层得到的shopCategoryList列表给设置到模型中
        * 4.如果报错的就用catch包住并给出来错误信息
        *
        二、再去获取头条信息
        * 1，还是先定一个list来装你要从service层中拿到的数据
        * 2. 因为你查询的是店铺状态码为“1”的店铺列表，所以呀你需要先定一个实体来把实体的这个属性给设置进去，然后当做一个参数传给service层方法
        * 3. 然后把得到的list集合设置给模型中
        * 4. 如果错误就需要去给出错误信息
        * */
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        //获取一级店铺列表
        try {
           shopCategoryList = shopCategoryService.getShopCategoryList(null);
           //然后把数据放到模型里面
            modelMap.put("shopCategoryList",shopCategoryList);
        }
        catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errorMsg",e.getMessage());
            return modelMap;
        }
        //以为需要获取头条信息的列表，所以需要在定义一个集合
        List<HeadLine> headLineList = new ArrayList<HeadLine>();
        try {
            //1.然后是获取所有的店铺信息状态为（1）的所有店铺，因为店铺的状态为1的时候才是正常的,所以需要定一个实体来当做参数穿进去
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            //然后通过service层来获取头条信息的列表，并且保存到刚刚定义的号的列表中
           headLineList = headLineService.getHeadLineList(headLineCondition);
           //然后把这个集合放到模型中
            modelMap.put("headLineList",headLineList);

        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errorMsg",e.getMessage());
            e.printStackTrace();
            return modelMap;
        }
            modelMap.put("success",true);
            return modelMap;
    }
}
