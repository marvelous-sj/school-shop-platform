package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.Product;

public interface ProductMapper {
	//添加商品
	int insertProduct(Product product);
	//根据id查询商品
	Product queryProductById(long productId);
	//修改商品信息
	int updateProduct(Product product);
	/**
	 * 分页查询，根据 商品名称(like),商品状态，店铺id，商品类别 查询.
	 * @param productCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
	//计数
	int queryProductCount(@Param("productCondition") Product production);
	//删除商品类别前先把该商品类别下所有商品的categoryid设置为null
	int updateProductCategoryToNULL(long productCategoryId);
}
