package com.imooc.o2o.service;

import com.imooc.o2o.entity.ShopCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopCategoryService {

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
