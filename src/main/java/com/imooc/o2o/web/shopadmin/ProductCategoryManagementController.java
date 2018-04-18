package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
    public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    //得到一个商品的商品信息的全部商品
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
        //首先先new一个shop，把得到的shopId给保存进去
//        Shop shop = new Shop();
//        shop.setShopId(1L);
//        //然后把这个shop对象通过session设置给当前的一个currentShop
//        request.getSession().setAttribute("currentShop", shop);
        //然后在通过session来取出你刚刚设置的shop对象那个,并且保存给新的currentShop对象中
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //先创建一个List的集合来装这些商品信息的框，判断得到的currentShop是不是为空
        List<ProductCategory> list = null;
        //判断currentShop里面是不是拿到了参数，或者是否为空进行返回Result类型（这个是自定义的）
        if (currentShop!=null && currentShop.getShopId()>0){
            return new Result<List<ProductCategory>>(true,list);
        }
        else {
            ProductStateEnum ps = ProductStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
        }

    }

    @RequestMapping(value = "/addproductecategorys",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProducteCategorys(@RequestBody List<ProductCategory> productCategoryList,HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<String, Object>();
        //这个是从用户的请求中带有的session中取出currentShop
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //遍历productCategoryList中的每一个,productCategory的内容
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        //判断productCategory是不是从用户的带过来的请求中获取到
        if (productCategoryList != null && productCategoryList.size() > 0) {

            try {
                //从service层去调用批量增加
                ProductCategoryExecution pe = productCategoryService.batchInsertProductCategory(productCategoryList);
                //从service层中得到的处理结果返回的状态码来进行判断是不是跟ProductCategoryStateEnum中事先定义好的状态码进行判断
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("error", e.toString());
                return modelMap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入至少一个商品");
        }

        return modelMap;
    }



}
