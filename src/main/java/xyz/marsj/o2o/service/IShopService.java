package xyz.marsj.o2o.service;

import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.dto.ShopExecution;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.exceptions.ShopOperationException;

public interface IShopService {
	//获取同一用户商品列表
	ShopExecution queryShopList(Shop shopCondition,int pageIndex,int pageSize);
	//根据Id获取店铺信息
	Shop queryByShopId(long ShopId);
	//修改店铺信息
	ShopExecution modifyShop(Shop shop,ImgHolder thumbnail) throws ShopOperationException;
	//注册店铺
	ShopExecution addShop(Shop shop,ImgHolder thumbnail);
	
}
