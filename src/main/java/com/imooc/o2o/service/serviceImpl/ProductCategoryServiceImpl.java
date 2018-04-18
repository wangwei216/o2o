package com.imooc.o2o.service.serviceImpl;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exception.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
   @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }
    //实现批量插入店铺商品信息
    @Override
    @Transactional
    public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) throws Exception {

        //这个是对得到的商品信息列表进行判断是否为空
        if(productCategoryList!=null && productCategoryList.size()>=0){

            try {
                //通过dao层的调用去看是否从数据库中查到数据，如果没有查到数据的话
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum<=0){
                    throw new ProductCategoryOperationException("店铺插入失败");
                }
                else {

                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }
            catch (Exception e){
                throw new ProductCategoryOperationException("batchInsertProductCategory error"+e.getMessage());
            }

        }
        else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
        
    }
}
