package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.ProductCategory;

public interface ProductCategoryMapper {
	// 根据shopId查询对应productCategory列表
	List<ProductCategory> queryProductCategoryList(long shopId);
	// 批量增加productCategory
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	//根据shopId和productCategoryId删除商品分类
	int delectProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId")long shopId);
}
