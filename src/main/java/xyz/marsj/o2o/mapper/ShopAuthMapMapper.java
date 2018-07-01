package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.ShopAuthMap;

public interface ShopAuthMapMapper {
	//分页查询映射,顾客信息,店铺,顾客姓名,奖品名,状态
	List<ShopAuthMap> queryShopAuthMapList(@Param("shopId")long shopId ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	//查询映射总数
	int queryShopAuthMapCount(@Param("shopId") long shopId);
	//根据Id查询对应映射
	ShopAuthMap queryShopAuthMapById(Long shopAuthId);
	//新增映射
	int insertShopAuthMap(ShopAuthMap shopAuthMap);
	//更新映射(ShopAuthId+userId)
	int updateShopAuthMap(ShopAuthMap shopAuthMap);
	int deleteShopAuthMap(long shopAuthId);
}
