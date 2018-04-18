package com.imooc.o2o.exception;

public class ProductCategoryOperationException  extends RuntimeException{


    private static final long serialVersionUID=2361446884822298909L;
    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}
