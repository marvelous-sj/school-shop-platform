package xyz.marsj.o2o.service;

import xyz.marsj.o2o.dto.UserShopMapExecution;
import xyz.marsj.o2o.entity.UserShopMap;

public interface IUserShopMapService {
	UserShopMapExecution getUserShopMapList(UserShopMap userShopMap ,int pageIndex,int pageSize);
	UserShopMap getUserShopMap(Long userId,Long shopId);

}
