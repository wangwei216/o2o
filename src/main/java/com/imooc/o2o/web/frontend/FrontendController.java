package com.imooc.o2o.web.frontend;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
    * 主页面的路由设置
    * */
    @Controller
    @RequestMapping("/frontend")
public class FrontendController {

        //这个是直接转发到功能首页界面的路由
        @RequestMapping(value = "/index",method = RequestMethod.GET)
        private String index(){
            return "/frontend/index";
        }
        /*
        * 这个是商品列表的路由页面
        * */
        @RequestMapping(value = "/showlist",method = RequestMethod.GET)
        private String showShopList(){

            return "/frontend/shoplist";
        }
        /*
        * 这个是店铺详情页的路由
        * */
        @RequestMapping(value = "/shopdetail",method = RequestMethod.GET)
        private String showShopDetail(){
            return "/frontend/shopdetail";
        }
        /*
        * 这个是商品信息的详情页的路由
        * */
    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    private String showProductDetail() {
        return "frontend/productdetail";
    }
}
