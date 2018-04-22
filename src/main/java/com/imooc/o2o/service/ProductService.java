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
    ProductExecution addProductImgList(Product product,List<ImageHolder> productImgHolderList)
            throws ProductCategoryOperationException;

    // 通过商品id productId来查询唯一的商品信息，返回的是商品类型信息
    Product getProductById(long productId); //这个其实就是在你去编辑之前先获得商品id

   /*  这个是修改商品信息的对应DAO层接口的updateProduct修改方法 ，返回的是你实现定义好的ProductException的返回的DTO
    其实也就是你在DTO中封装好的执行结果,thumbnail这个实体里面封装了文件流的名字和该文件二进制流*/
    ProductExecution modifyProduct(Product product,
                                   ImageHolder thumbnail,
                                   List<ImageHolder> productImgHolderList)
            throws ProductCategoryOperationException;







}
