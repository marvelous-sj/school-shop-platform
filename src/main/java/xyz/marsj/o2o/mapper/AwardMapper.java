package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.Award;

public interface AwardMapper {
	//分页查询奖品,奖品名,所属店铺,状态
	List<Award> queryAwardList(@Param("awardCondition")Award awardCondition ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	//查询奖品总数
	int queryAwardCount(@Param("awardCondition") Award awardCondition);
	//根据Id查询对应奖品
	Award queryAwardByAwardId(long awardId);
	//新增奖品
	int insertAward(Award award);
	//更新奖品
	int updateAward(Award award);
	//删除奖品(awardId+shopId)
	int deleteAward(@Param("awardId")long awardId,@Param("shopId")long shopId);

}
