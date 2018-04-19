package com.imooc.o2o.service.serviceImpl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exception.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private  ProductDao productDao;
    @Resource
    private ProductImgDao productImgDao;

    //后面的参数是一个列表问题
    /*
     * 1.处理缩略图，获取缩略图相对路径并赋值给product
     * 2.往tb_product中写入商品信息，获取shopId
     * 3.结合product批量处理商品详情图
     * 4.将商品详情图列表批量插入tb_product_img中
     *
     * */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductCategoryOperationException {
       //先进行空值的判断
        if (product!=null && product.getShop()!=null && product.getShop().getShopId()!=null){
            //给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架的状态
            product.setEnableStatus(1);
            //如果商品缩略图不为空就添加进去
            if (thumbnail!=null){
//这个方法一会再去实现
                addThumbnail(product,thumbnail);
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                //到这对调用dao层的方法进行调用判断是不是插入影响到数据库了
                if (effectedNum<=0){
                    throw new ProductCategoryOperationException("创建商品失败");
                }

            }catch (Exception e){
                throw new ProductCategoryOperationException("创建商品失败"+e.toString());
            }
         //如果传进来的product是空值的话，就不能添加
            if (productImgHolderList!=null&&productImgHolderList.size()>0){
               //这个方法一会再去实现
                addProductImgList(product,productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }
        else {
            //如果传进来的参数为空的话 就返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }

    }

    /*
    *
    * 这个是批量增加商品图片信息
    * */
    @Override
    public ProductExecution addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        //首先获取图片存储路径，直接放到相应店铺的文件夹下
        String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片然后进行一次去处理
        for (ImageHolder productImgHolder:productImgHolderList){
          String imgAddr =  ImageUtil.generateNormalImg(productImgHolder,dest);
          //定义一个productImg
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            //把设置成功的给添加到productImgList商品图片列表中
            productImgList.add(productImg);
            //到了这里

        }
        return null;
    }


    /*
        * 这个是添加缩略图
        *
        * */
        private void addThumbnail(Product product, ImageHolder thumbnail) {
            String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
            String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
            product.setImgAddr(thumbnailAddr);
        }





}
