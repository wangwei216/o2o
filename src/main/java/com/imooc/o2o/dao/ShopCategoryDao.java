package com.imooc.o2o.dao;


import com.imooc.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//这个是商品店铺分类信息的功能接口
public interface ShopCategoryDao {
        //这个是把实体对象ShopCategory绑定到shopCategoryCondition的类型当中
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategory);

}
