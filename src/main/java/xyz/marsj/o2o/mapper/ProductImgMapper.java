package xyz.marsj.o2o.mapper;

import java.util.List;

import xyz.marsj.o2o.entity.ProductImg;

public interface ProductImgMapper {
	//批量增加图片
	int batchInsertProductImg(List<ProductImg> productImgList);
	//删除对应productId下所有详情图
	int deleteProductImgByProductId(long productId);
	//查询对应productId下所有详情图 
	List<ProductImg> queryProductImgList(long productId);
}
