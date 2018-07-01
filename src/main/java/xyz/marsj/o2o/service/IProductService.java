package xyz.marsj.o2o.service;

import java.util.List;

import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.dto.ProductExecution;
import xyz.marsj.o2o.entity.Product;

public interface IProductService {
	//添加商品信息
	ProductExecution addProduct(Product product,ImgHolder thumbnail,List<ImgHolder> thumbnailList) ;
	//修改商品信息
	ProductExecution modifyProduct(Product product,ImgHolder thumbanil,List<ImgHolder> thumbnailList);
	//根据Id查询商品
	Product queryProductById(long productId);
	//获取店铺下的商品列表
	ProductExecution queryProductList(Product productCondition,int pageIndex,int pageSize);
	
}
