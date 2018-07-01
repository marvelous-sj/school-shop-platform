package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.UserAwardMap;

public interface UserAwardMapMapper {
	//分页查询映射,顾客信息,店铺,顾客姓名,奖品名,状态
	List<UserAwardMap> queryUserAwardMapList(@Param("userAwardMapCondition")UserAwardMap userAwardMapCondition ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	List<UserAwardMap> queryUserAwardMapListFrontend(@Param("userAwardMapCondition")UserAwardMap userAwardMapCondition ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	//查询映射总数
	int queryUserAwardMapCount(@Param("userAwardMapCondition") UserAwardMap userAwardMapCondition);
	int queryUserAwardMapCountFrontend(@Param("userAwardMapCondition") UserAwardMap userAwardMapCondition);
	//根据Id查询对应映射
	UserAwardMap queryUserAwardMapById(long userAwardMapId);
	UserAwardMap queryUserAwardMapByIdFrontend(long userAwardMapId);
	//新增映射
	int insertUserAwardMap(UserAwardMap userAwardMap);
	//更新映射(userAwardId+userId)
	int updateUserAwardMap(UserAwardMap userAwardMap);

}
