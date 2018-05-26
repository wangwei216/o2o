package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /*
    *获取店铺信息以及店铺下的商品列表信息
    *
    * */
    @RequestMapping(value = "/listshopdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopDetailPageInfo(HttpServletRequest request){
        /*
        * 1.先定义一个模型来接受数据（这里还需要考虑你需要获取到什么就先定义好list列表）
        * 2.获取店铺的id（你获取到的id都需要用一个实体来进行接收数据）
        * 3.拿到shopId你需要先进行判断
        * 4.当拿到的shopId不为-1的话，就通过shopService对象去调用通过店铺id获取店铺信息接口
        * 5.把shop实体和list列表都放到模型里面
        * 6.失败就返回错误信息
        * */
        Map<String,Object> modelMap = new HashMap<String,Object>();
        Shop shop = null;
        //用来装得到的商品类别列表
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();

        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if (shopId!= -1){
          shop  = shopService.getByShopId(shopId);
           productCategoryList = productCategoryService.getProductCategoryList(shopId);
           modelMap.put("shop",shop);
           modelMap.put("productCategoryList",productCategoryList);
           modelMap.put("success",true);
        }
        else {
            modelMap.put("success",false);
            modelMap.put("errorMsg","shopId is empty");
        }
                   return modelMap;
    }

    /*
    * 这个是根据查询条件分页列出店铺下的所有商品信息
    * */
    @RequestMapping(value = "/listproductsbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listProductsByShop(HttpServletRequest request){
        /*
        * 1,获取页码，每一页需要的条数
        * 2.获取店铺id
        * 3.然后对这三个值进行判断不为空就是大于 -1
        *   4.满足条件就再去从前台调出来ProductCategoryId，在获取前台输进去的模糊查询的商品名字
        *   5.然后组合查询呢条件（作为你要查询product列表的参数）
        *   6.根据参数返回响应的商品信息列表和总条数
        *   7.然后设置到模型里面
        *
        * */
        Map<String,Object> modelMap = new HashMap<String, Object>();
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int shopId = HttpServletRequestUtil.getInt(request, "shopId");
        if ((pageIndex> -1)&&(pageSize> -1)&&(shopId> -1) ){

            int productCategoryId = HttpServletRequestUtil.getInt(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            //因为要查询的是product对象的列表和总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count",pe.getCount());
            modelMap.put("success",true);
        }
        else {
            modelMap.put("success",false);
            modelMap.put("errorMsg","pageIndex is null or pageSize is null or shopId is null");
        }
            return modelMap;

    }



    /*
     * 这个是获取组合之后的查询条件,目的是为了模糊查询得到商品的列表及总数信息
     * */
    private Product compactProductCondition4Search(int shopId,
                                                   int productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        productCondition.setEnableStatus(1);
        return productCondition;
    }

}
