package xyz.marsj.o2o.service;

import java.util.List;

import xyz.marsj.o2o.dto.ProductCategoryExecution;
import xyz.marsj.o2o.entity.ProductCategory;
import xyz.marsj.o2o.exceptions.ProductCategoryOperationException;

public interface IProductCategoryService {
List<ProductCategory> queryProductCategoryList(long shopId);
//批量增加商品类别
ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;
//先删除 商品中对应的商品类别id，再删除商品类别
ProductCategoryExecution delectProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;
}
