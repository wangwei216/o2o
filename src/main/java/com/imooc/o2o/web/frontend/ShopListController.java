package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;

	/*
	*
	*	返回商品列表里的shopCategory列表（一级还是二级），以及区域信息列表
	* */
	@RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int parentId = HttpServletRequestUtil.getInt(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1) {
			try {
				//创建这两个对象都是为了能够把值设置进去
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(parentId);
				shopCategoryCondition.setParentId(parentId);//这个应该是setParent这个方法 shopCategoryCondition就是为了设置参数
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
			}
			catch (Exception e){
				modelMap.put("success",false);
				modelMap.put("errorMsg",e.getMessage());
				return modelMap;
			}

		} else {
			try {
				//如果parentId不为空的话，取出所有一级列表，也就是全部的商店列表
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}
		//把取到的数据都放到模型中
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Area> areaList = null;
		try {
			areaList = areaService.getAreaList();
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}


	/*
	*
	* 获取指定查询条件下的店铺列表(也就是可以进行搜索然后可以进行匹配查询)
	*	1.先从前端获取参数
	*	2.对参数进行判断
	* */
	@RequestMapping(value = "/listshops", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//从前端获取pageIndex和pageSize
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//对拿到 的值进行判断
		if ((pageIndex > -1) && (pageSize > -1)) {
			int parentId =  HttpServletRequestUtil.getInt(request, "parentId");
			int shopCategoryId = HttpServletRequestUtil.getInt(request,
					"shopCategoryId");
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			String shopName = HttpServletRequestUtil.getString(request,
					"shopName");
			Shop shopCondition = compactShopCondition4Search(parentId,
					shopCategoryId, areaId, shopName);
			ShopExecution se = shopService.getShopList(shopCondition,
					pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}

		return modelMap;
	}
	/*
	* 这个是获取组合之后的查询条件
	* */
	private Shop compactShopCondition4Search(int parentId, int shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
		//这个是查询某个一级ShopCategory 下面的所有二级shopCategory里面的店铺列表(一级就相当于是一个大的分类，二级就相当于是这个分类下对应的店铺)
		if (parentId != -1L) {
			ShopCategory parentCategory = new ShopCategory();
			ShopCategory childCategory = new ShopCategory();

			parentCategory.setShopCategoryId(parentId);
			shopCondition.setParentCategory(parentCategory);
		}
		//这个是查询二级shopcategory下面的店铺列表
		if (shopCategoryId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		//查询位于某个区域下的店铺列表
		if (areaId != -1L) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}

		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}
		//前端展示的店铺都是审核成功的店铺
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}




}
