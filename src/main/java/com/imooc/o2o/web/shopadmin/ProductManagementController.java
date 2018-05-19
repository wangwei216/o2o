package com.imooc.o2o.web.shopadmin;


        /*
        *
        * 这个是商品管理界面的controller层
        * 实现的是商品的添加的controller层的实现
        *
        *
        * */


import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exception.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    private static final int IMAGEMAXCOUNT = 6;

    /*
     * 一、如何添加对商品controller层的设计思路
     *   1.先对前台的验证码进行验证
     *   2.创建一个mapper的实体对象用来接收接收前台参数的变量的初始化，包括商品、缩略图、详情图列表实体类
     *
     *
     * */
    @RequestMapping(value = "/addproduct", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //验证码的效验
        if (CodeUtil.checkVerifyCode(request)) {
            modelMap.put("sucess", false);
            modelMap.put("error", "输入了错误的验证码");
            return modelMap;
        }
        //接收前台参数的变量的初始化，包括商品、缩略图、详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        //从用户的request获取从前台传过来的,productStr这个是要和前台的字段名进行对应起来的
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        //这个是用来处理文件流的
        MultipartHttpServletRequest multipartRequest = null;
        //这个是用来保存缩略图的文件流以及它的名字
        ImageHolder thumbnail = null;

        //用来保用商品详细的图片列表以及对应的名字列表
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        //从request的session中获取到文件流的
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            //看请求中是否存在上传文件流需要的，如果有则取出相关的文件包括（缩略图和商品详情图）
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                //把缩略图的
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                //获取到缩略图的文件流及名字
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                //取出缩略图并构建ImageHolder对象,从thumbnail这个key里面去获取他的文件流
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    //遍历得到商品图片的文件流
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    //对得到的图片文件流进行判断
                    if (productImgFile != null) {
                        //当取到第i个不为空的文件流的时候，就把其加入到详情图列表
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        productImgList.add(productImg);
                    } else {
                        break;
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            //然后再去生产product实例，通过前端传过来的表单String流将其转化为Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
            //如果Product信息、缩略图及详情图片列表为非空的话，就开始商品添加操作
            if (product != null && thumbnail != null && productImgList.size() > 0) {

                try {
                    //从session中取出店铺 shopId赋值给Product
                    //为了减少对前端的依赖，这个shopId需要从后端进行set进去，也为了防止用户随便插入，这个currentShop是先从前端获取
                    Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                    Shop shop = new Shop();
                    shop.setShopId(currentShop.getShopId());
                    product.setShop(shop);

                    //然后开始执行添加操作
                    ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                    //看执行service层的方法的时候返回的枚举状态码是什么
                    if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                        modelMap.put("success", true);
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("error", pe.getStateInfo());

                    }
                } catch (ProductCategoryOperationException e1) {
                    modelMap.put("success", false);
                    modelMap.put("error", e1.toString());
                    return modelMap;
                }
            }
            //就返回提示信息商品信息不能为空
            else {
                modelMap.put("success", false);
                modelMap.put("errorMsg", "请输入商品信息");
            }
            return modelMap;
        }

        /*
        *
        * 通过商品id获取店铺信息的id，再去通过商品信息的
        *
        * */
        @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
        @ResponseBody
        private Map<String,Object> getProductById(@RequestParam Long productId){
            Map<String,Object> modelMap = new HashMap<String, Object>();
            //先对穿件来的productId进行判空‘
            if (productId> -1){
                //获取商品信息，通过service层的功能接口实现
                Product product = productService.getProductById(productId);
                //因为你要想获得所有的商品信息，必须得先获取这个店铺的id再通过这个店铺的的shopId去得到店铺下的所有商品信息
                List<ProductCategory> productCategoryList =
                        productCategoryService.getProductCategoryList(product.getShop().getShopId());
                modelMap.put("product",product);
                modelMap.put("productCategoryList",productCategoryList);
                modelMap.put("success",true);
            }
            else {
                modelMap.put("success",false);
                modelMap.put("errorMsg","productId 为空");
            }

            return modelMap;
        }

        /*
        * 这个是商品的编辑
        *
        */

         @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
        @ResponseBody
        private Map<String, Object> modifyProduct(HttpServletRequest request) throws IOException {
           boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
            Map<String, Object> modelMap = new HashMap<String, Object>();
            if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "输入了错误的验证码");
                return modelMap;
            }
            //接受前端的参数的变量的初始化，包括商品，缩略图，详情图列表
            ObjectMapper mapper = new ObjectMapper();
            Product product = null;
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            MultipartHttpServletRequest multipartRequest = null;
            ImageHolder thumbnail = null;
            List<ImageHolder> productImgHolderList = new ArrayList<ImageHolder>();

            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //判断是不是存在文件流，存在则取出文件
            if (multipartResolver.isMultipart(request)) {
                multipartRequest =  (MultipartHttpServletRequest) request;
                //取出缩略图并构建ImageHolder对象
             CommonsMultipartFile   thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                if (thumbnailFile!=null){
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                }

                //取出详情图片列表
             for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
                            .getFile("productImg" + i);
                    if (productImgFile != null) {
                        //这个是吧详情图片文件流不为空的，加入到详情图列表中去
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                        productImgHolderList.add(productImg);
                    }
                    else {
                        //如果是取出的第i个详情图片文件流为空，终止循环
                        break;
                    }
                }
            }
            try {
                //从前端传进来的string文件流转化为product实体类
                product = mapper.readValue(productStr, Product.class);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }


            //非空判断
            if (product  != null) {
                try {
                    Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                    product.setShop(currentShop);
                    ProductExecution pe = productService.modifyProduct(product,thumbnail,productImgHolderList);
                    if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                        modelMap.put("success", true);
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", pe.getStateInfo());
                    }
                } catch (RuntimeException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.toString());
                    return modelMap;
                }

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "请输入商品信息");
            }
            return modelMap;
        }


        /*
        * 通过店铺的id来获取该店铺下的商品列表：
        *
        *   1.其实就是先从前台获取到你service层需要用到的参数或者从当前客户端的request、session中获取你需要的数据
        *   2.然后对你拿到的数据进行各种逻辑判断
        *
        * */
        @RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
        @ResponseBody
        private Map<String,Object> getProductListByShop(HttpServletRequest request){
            //还是吸纳定义一个modelMap集合来进行保存数据
            Map<String,Object> modelMap = new HashMap<String, Object>();
            //因为你调用Service层中getProductList的方法时，你需要先要从前台获取到你需要传进来的参数pageIndex，pageSize
            int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
            int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
            //然后需要从当前请求过来的session中再去获取你需要的店铺信息，也就是获取shopId，这个currentShop一般是从前台传进来的
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                //队拿到的数据进行判断
                if ((pageIndex> -1)&&(pageSize> -1)&&(currentShop!=null) ){
                    //这个是获取传入的时候需要检索的条件，可以从某个商品类别查找商品名去筛选这个店铺下的商品列表信息
                    long prodcutCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
                    String productName = HttpServletRequestUtil.getString(request, "productName");
                    Product productCondition = compactProductCondition(currentShop.getShopId(), prodcutCategoryId, productName);
                    //然后传入查询条件及分页信息，返回相应列表的总数,也就是直接利用上面从前端得到的参数
                    ProductExecution pe= productService.getProductList(productCondition,pageIndex,pageSize);
                    modelMap.put("productList",pe.getProductList());
                    modelMap.put("count",pe.getCount());
                    modelMap.put("success", true);
                }
                else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg","pageIndex or pageSize or shopId is NULL");
                }
                return modelMap;

        }

        /*
        *
        * 这个是一个封装的方法：就是封装了商品的查询条件到Product实例中
        * */
        private Product compactProductCondition(int shopId,long prodcutCategoryId,String productName){
            Product productCondition = new Product();
            Shop shop = new Shop();
            //把传进来的shopId设置到shop中
            shop.setShopId(shopId);
            productCondition.setShop(shop);
            //看是不是还有其他的要设置类别的要求，有 话就就set进去
            if (prodcutCategoryId !=-1L){

                ProductCategory productCategory = new ProductCategory();
                productCategory.setProductCategoryId(prodcutCategoryId);
                //把整个ProductCategory实体直接给set到Product中去
                productCondition.setProductCategory(productCategory);
            }
            if (productName!=null){
                productCondition.setProductName(productName);
            }
                return productCondition;
        }


}
