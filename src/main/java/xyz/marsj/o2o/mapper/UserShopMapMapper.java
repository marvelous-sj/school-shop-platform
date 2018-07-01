package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.UserShopMap;

public interface UserShopMapMapper {
	//分页查询映射,顾客信息,店铺信息,店铺名称,用户名称,时间
	List<UserShopMap> queryUserShopMapList(@Param("userShopMapCondition")UserShopMap userShopMapCondition ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	//查询映射总数
	int queryUserShopMapCount(@Param("userShopMapCondition") UserShopMap userShopMapCondition);
	//根据Id查询对应映射
	UserShopMap queryUserShopMap(@Param("userId")long userId,@Param("shopId")long shopId);
	//新增映射
	int insertUserShopMap(UserShopMap userShopMap);
	//更新映射
	int updateUserShopMap(UserShopMap userShopMap);

}
