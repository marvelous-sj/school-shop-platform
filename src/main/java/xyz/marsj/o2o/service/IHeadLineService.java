package xyz.marsj.o2o.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.HeadLine;

public interface IHeadLineService {
	public static final String HLLISTKEY="headlinelist";
	//获取首页轮播图信息
	List<HeadLine> getHeadLineList(@Param("headLineCondition") HeadLine headLineCondition);
}
