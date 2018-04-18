package com.imooc.o2o.service.serviceImpl;


import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.dto.ShopOperationException;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	/**
	 * 使用注解控制事务方法的优点： 1.开发团队达成一致约定，明确标注事务方法的编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
	 */
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {

		//判断店铺是否为空
		if (shop == null)

		{
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}
		try {
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new RuntimeException("店铺创建失败");
			} else {
				try {
					if (thumbnail.getImage() != null) {
						addShopImg(shop, thumbnail);
						effectedNum = shopDao.updateShop(shop);
						if (effectedNum <= 0) {
							throw new RuntimeException("创建图片地址失败");
						}
					}
				} catch (Exception e) {
					throw new RuntimeException("addShopImg error: " + e.getMessage());
				}

			}
		}
		catch (Exception e) {
			throw new RuntimeException("insertShop error: " + e.getMessage());
		}
	return null;
}


	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		String dest = FileUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr);
	}
	/*通过id得到shop信息*/
	@Override
	public Shop getByShopId(long shopId) {

		return shopDao.queryByShopId(shopId);
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, ImageHolder  thumbnail) throws ShopOperationException {
		/*
		* 1，判断是否需要处理图片
		* 2，更新店铺信息
		* */
		if (shop==null||shop.getShopId()==null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			//开始判断是否需要处理图片，如果图片存在的话，就需要先删除掉图片的文件夹
			try {
				//判断图片的文件流是否为空，文件名不为空，且空格不等于空
				try {
					if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
						Shop temShop = shopDao.queryByShopId(shop.getShopId());
						//判断得到的零时shop不为空时，就删除
						if (temShop.getShopImg() != null) {
							//这里有问题，没有写删除文件的工具类
							ImageUtil.deleteFileOrPath(temShop.getShopImg());
						}
//这个地方有问题
					addShopImg(shop,thumbnail);
						//这里开始更新店铺信息
					}
					shop.setLastEditTime(new Date());
					int effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						return new ShopExecution(ShopStateEnum.INNER_ERROR);
					} else {
						shop = shopDao.queryByShopId(shop.getShopId());
						return new ShopExecution(ShopStateEnum.SUCCESS);
					}
				} catch (Exception e) {
					throw new ShopOperationException("modifyShop error" + e.getMessage());
				}
			}
			catch (Exception e){
				throw new ShopOperationException(e.getMessage());
			}
		}

	}
	/*根据shopCondition分页返回响应的数据来实现service的接口*/
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		//这一步就是把pageIndex页面的下标第几页，pageSize就是一页分页数量是多少，
		// 有这两个值就可以转化为分页所需要的参数rowIndex这个就是每次查询的其实位置
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
		//然后利用这三参数就可以
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		//这步是获取全部shop的列表总数
		int count = shopDao.queryShopCount(shopCondition);
		//这里需要定以一个空的ShopExecution来接受数据
		ShopExecution se = new ShopExecution();
		//判断有没有查询到所需要的店铺数据,如果不为空的话，就把shopList和查询到的全部shop信息的总数设置进去
		if (shopList!=null){
			se.setShopList(shopList);
			se.setCount(count);
		}
		else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		//无论是否成功，都会把这个方法返回的ShopExecution给返回回去
		return se;
	}


}
