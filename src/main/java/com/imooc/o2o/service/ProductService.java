package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exception.ProductCategoryOperationException;

import java.util.List;

public interface ProductService {

    /*添加商品信息以及对图片的处理*/
    //thumbnail这个是图片的缩略图的属性
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImages)
            throws ProductCategoryOperationException;

    //添加 也就是缩略图信息
    ProductExecution addProductImgList(Product product,List<ImageHolder> productImgHolderList);

}
