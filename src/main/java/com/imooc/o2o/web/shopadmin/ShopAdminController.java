package com.imooc.o2o.web.shopadmin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*一定要记这所有的controller层都需要在requestMapping的路径前面都要加上“/”,而且这个是控制所有请求的路由设置*/
@Controller
@RequestMapping(value = "/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {

    @RequestMapping("/shopoperation")
    public String shopOperation() {
        return "/shop/shopoperation";
    }

    //转发到商品类别管理界面
    @RequestMapping(value = "/productcategorymanage",method = RequestMethod.GET)
    private String productCategoryManagement(){
            return "/shop/productcategorymanage";
    }
    //转发到店铺列表界面
    @RequestMapping("/shoplist")
    private String shopList(){
        return "/shop/shoplist";
    }

    //转发到店铺类别管理界面
    @RequestMapping("/shopmanage")
    private String shopManagement(){
        return "/shop/shopmanage";
    }

    //转发到商品添加的页面
    @RequestMapping("/productoperatrion")
    private String productOperatrion(){
        return "shop/productoperation";
    }

    //转发值商品管理页面
    @RequestMapping("/productmanage")
    private String productManagement(){
        return "shop/productmanage";
    }

}
