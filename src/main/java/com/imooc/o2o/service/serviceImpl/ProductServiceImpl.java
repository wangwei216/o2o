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
import com.imooc.o2o.util.PageCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
    private Product product;
    private CommonsMultipartFile thumbnail;
    private List<ImageHolder> productImgHolderList;

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
        if (product!=null && product.getShop()!=null && product.getShop().getShopId()> -1){
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

            //到了这里如果有图片进行添加的话，就开始执行批量添加的操作。也就是判断productImgList列表里面的长度是不是大于0
            if(productImgList.size()>0){
                try {
                    //通过调Dao层的方法去看是否拿到了数据
                    int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                    if (effectedNum<=0){
                        throw  new ProductCategoryOperationException("创建商品信息详情失败");
                    }
                }
                catch (Exception e){
                    throw new ProductCategoryOperationException("创建商品信息详情失败"+e.toString());
                }
            }

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

    // 通过商品id productId来查询唯一的商品信息，返回的是商品类型信息
    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductByProductId(productId);
    }


    /*
    * 删除某个商品下所有的详情图片
    * */

    private void deleteProductImgList(long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg : productImgList) {
            FileUtil.deleteFile(productImg.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }

    //根据DAO中的接口传入的商品实体类去修改商品信息，返回的是int，实现的是DAO层修改商品信息updateProduct

    /*
    * 1、先看缩略图参数是不是有值，有的话则处理缩略图
    *    如果之前已经有缩略图的话，需要先删除缩略图再添加新图，之后获取缩略图的的相对路径再赋值给product
    * 2、再看商品详情列表参数是否有值，对商品详情图片列表进行同样的操作
    * 3、将tb_producdt_img下面原先的商品详情记录全部清除
    * 4、更新tb_product的信息
    *
    *
    * */
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductCategoryOperationException {

        //先进行空值
        if(product!=null && product.getShop()!=null && product.getShop().getShopId()> -1){
            product.setLastEditTime(new Date());
            if(thumbnail!=null){
                //先获取原有的缩略图信息
                Product tempProduct = productDao.queryProductByProductId(product.getProductId());
                //从获取到的当前商品信息中判断是不是可能获取到图片地址
                if (tempProduct.getImgAddr()!=null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                //调用添加缩略图的方法
                addThumbnail(product,thumbnail);
            }
            //再去判断有没有新存入的商品详情图列表，如果有的话就先删除原来的，再去添新的
            if (productImgHolderList!=null && productImgHolderList.size()>0){

                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgHolderList);
            }
            try {
                //更新商品信息
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum<0)
                {
                    throw new ProductCategoryOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
             }
             catch (Exception e){
                throw new ProductCategoryOperationException("更新商品信息失败"+e.toString());
             }
        }
        else {
                return   new ProductExecution(ProductStateEnum.EMPTY);
        }


    }

    /*
    * 从DAO层查询商品列表，并分页实现，可以输入的条件是商品名、商品状态、店铺ID、商品类别处理得到的数据
    * */
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //需要先把页码转化为数据库的行码，调用DAO取回，
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        //rowIndex查询的起始位置，pageSize表示一页规定显示多少条数据
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        //再去查询一共有多少条数据，就能实现分页了
        int count = productDao.queryProductCount(productCondition);
        //创建一个返回的ProductExecution对象，然后把查询到的商品信息列表set进去，还有查询的总数
        ProductExecution pe= new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }


}
