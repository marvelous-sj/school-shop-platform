package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.UserProductMap;

public interface UserProductMapMapper {
	//分页查询映射,顾客信息, 店铺信息,顾客名字 模糊 ,商品名字 模糊, 消费日期
	List<UserProductMap> queryUserProductMapList(@Param("userProductMapCondition")UserProductMap userProductMapCondition ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	//查询映射总数
	int queryUserProductMapCount(@Param("userProductMapCondition") UserProductMap userProductMapCondition);
	//新增映射
	int insertUserProductMap(UserProductMap userProductMap);
}
