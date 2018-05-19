package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.dto.ShopOperationException;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	/*
	* 这个是通过shopID查询到shop实体中国关联的其他所有表的信息的
	* 	1.先定义一个model来最终返回给前台
	* 	2.因为你调用service的时候需要一个shopId的参数来去查询shop实体所有对象，所以这里你需要从前台获取这个shopId
	* 	3.得到shopId之后，还需要对shopId进行去判断是不是拿到了数据
	* 	4.如果拿到数据的话，就直接去调用service层的方法拿到从DAO层查询shop之后的数据
	* 	5.这一步是基于前台进行考虑的，因为前台需要的还有区域信息Area列表，所以去调用areaService的方法去查询area的列表
	*
	* 	6.最重要的是你要把你查询得到的数据都给set进这个modelMap中去，前面是键后面是你查询得到的值,完事把成功的提示也给set进模型中
	*如果到这里你还需要保证项目的完整性，需要进行一些错误信息提示啊或者是try，catch啊
	* */
	@RequestMapping(value = "/getshopbyshopid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopByShopId(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		/*这里只是用了一二封装后的getparamater,所以说就算我不用那个封装好的也可以拿到数据
		String shopId1 = request.getParameter("shopId");*/
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1){
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop",shop);
				modelMap.put("areaList",areaList);
				modelMap.put("success",true);
			}
			catch (Exception e){
				modelMap.put("success",false);
				modelMap.put("errMsg",e.toString());
			}

		}
		else {
			modelMap.put("success",false);
			modelMap.put("errMsg","shopId is Null");
		}
			return modelMap;

	}


	@RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopInitInfo(){

		//首先要先为你想要获取的对象新建一个容器
		Map<String,Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		//然后去调用service层的方法去得到
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			//如果成功的话就把得到的shopCategoryList放到modelMap
			modelMap.put("shopCategoryList",shopCategoryList);
			modelMap.put("areaList",areaList);
			//放到模型中成功后就给个提示
			modelMap.put("sucess",true);

		}
		catch (Exception e){
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
		}
		return modelMap;
	}


	/*修改店铺信息*/
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,
				"statusChange");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		/*这里是处理图片的*/
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest
					.getFile("shopImg");
		}
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		shop.setShopId(currentShop.getShopId());

		//修改店铺信息
		if (shop != null && shop.getShopId() > -1) {
			ShopExecution se;
			try {

				if (shopImg==null) {
					se = shopService.modifyShop(shop,null);
				} else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
					se = shopService.modifyShop(shop,imageHolder);
				}
				if (se.getState()==ShopStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
				}
				else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e){
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入修改店铺信息");
		}
		return modelMap;
	}



	/*
	一、上传图片第一步：

	* 1.获取从前端传过来的图片信息
	* 2.并将其转化为实体类
	* 3.获取从前端传进来的文件流
	* 4.然后接收到shopImg中去
	*
	二、注册店铺
	*
	* 1.判断店铺和接收到的shopImg文件流是否为空
	* 2.
	*
	*
	*
	* */
	@RequestMapping(value = "/registershop", method = RequestMethod.GET)

	private Map<String, Object> registerShop(HttpServletRequest request) throws IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
			//
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			//把获取到的参数给装到实体类中
			shop = mapper.readValue(shopStr,Shop.class);
		}
		catch (Exception e){
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		//这个是用来实现上传文件的，
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver
				(request.getSession().getServletContext());
		//看request里面有没有上传的文件流，如果有文件流就想需要做一个把request转换为MultipartHttpServletRequest
		if (commonsMultipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				//把从获取过来的文件流转换为CommonsMultipartFile这个类型，spring能够处理的文件流
			shopImg =(CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

		}
		else {
			modelMap.put("success",false);
			modelMap.put("errMsg","上传照片不能为空");
			return modelMap;
		}

		//2.注册店铺

		if (shop != null && shopImg != null) {
			try {
				//这里是又重新改了一下代码，使用到了
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				shop.setOwnerId(user.getUserId());

				ShopExecution se = shopService.addShop(shop, imageHolder);

				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					// 若shop创建成功，则加入session中，作为权限使用
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession()
							.getAttribute("shopList");
					if (shopList != null && shopList.size() > 0) {
						shopList.add(se.getShop());
						request.getSession().setAttribute("shopList", shopList);
					} else {
						shopList = new ArrayList<Shop>();
						shopList.add(se.getShop());
						request.getSession().setAttribute("shopList", shopList);
					}
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}


	/*得到shopList的信息的前台*/
	@RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object>getShopList(HttpServletRequest request){

		Map<String ,Object> modelMap = new HashMap<String, Object>();
		//通过session来获取用户的信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		//因为没有登录，需要为用户设置一个默认值
		 user.setUserId(1);
		 int employeeId = user.getUserId();
		 List<Shop> shopList = new ArrayList<Shop>();
		 try {
		 	Shop shopCondition = new Shop();
/*这里有问题，本来视频上是这样写的，但是没有setOwner这个方法
			shopCondition.setOwner(user);*/
		 	ShopExecution se= shopService.getShopList(shopCondition,0,100);
		 	modelMap.put("shopList",se.getShopList());
		 	modelMap.put("success",true);
		 	modelMap.put("user",user);
		 }catch (Exception e){
		 	modelMap.put("success",false);
		 	modelMap.put("errMsg",e.getMessage());
		 }
		 return modelMap;
	}

	/*这个就相当于一个拦截器的作用看你有没有登录过，
	对你进行判断，然后给你重定向到不同的界面*/
	@RequestMapping(value = "/getshopmanagementinfo" ,method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
		Map<String ,Object> modelMap = new HashMap<String, Object>();
		//从用户的请求中获取信息
		int shopId = HttpServletRequestUtil.getInt(request, "shopId");
		//然后对得到的shopId进行判断
		if (shopId<=0){
			//看看能不能从用户请求的session中获取到你之前查询到的
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			//在对得到的这个对象进行判断是否为空，等于空的话就可以给他进行重定向
			if (currentShopObj==null){
				modelMap.put("redirect",true);
				modelMap.put("url","/o2o/shopadmin/shoplist");

			}
			else {
				//就把currentShopObj刚刚判断的对象给之前的那个currentShop对象
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect",false);
				modelMap.put("shopId",currentShop.getShopId());
			}
		}
		//到这就是如果没有得到shopId的值的话就直接把当前currentSHop给设置到session中去
		//但是不用再去重定向了
		else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop",currentShop);
			modelMap.put("redirect",false);
		}
		return modelMap;

	}









	//把照片的输入流转换为文件流的方法
	private static void inputStreamToFile(InputStream ins, File  file){
		OutputStream os= null;
		try {
			os =new FileOutputStream(file);
			//实际接收长度
			int bytesRead = 0;
			//因为读入的照片需要的字节文件，需要用字节数组，相当于每次读取的字节长度
			byte[] buffer =new byte[1024];
			while ((bytesRead = ins.read(buffer))!=-1){
					//然后把读入的文件流，都重新写到输出流中，
					os.write(buffer,0,bytesRead);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//如果出错了不关闭流，有可能会溢出
		finally {
			try {
				if (os!=null){
					os.close();
				}
				if (ins!=null){
					ins.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


}
