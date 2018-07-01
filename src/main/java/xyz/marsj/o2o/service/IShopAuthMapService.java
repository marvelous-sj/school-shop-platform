package xyz.marsj.o2o.service;

import xyz.marsj.o2o.dto.ShopAuthMapExecution;
import xyz.marsj.o2o.entity.ShopAuthMap;
import xyz.marsj.o2o.exceptions.ShopAuthMapOperationException;

public interface IShopAuthMapService {
	ShopAuthMapExecution listShopAuthMapListById(Long shopId,Integer pageIndex,Integer pageSize);
	ShopAuthMap getShopAuthMapById(Long shopAuthId);
	ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap)throws ShopAuthMapOperationException;
	ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap)throws ShopAuthMapOperationException;
}
