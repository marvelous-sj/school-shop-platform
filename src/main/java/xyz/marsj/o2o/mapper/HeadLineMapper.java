package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.HeadLine;

public interface HeadLineMapper {
	//获取首页轮播图信息
	List<HeadLine> queryHeadLineList(@Param("headLineCondition") HeadLine headLineCondition);

}
