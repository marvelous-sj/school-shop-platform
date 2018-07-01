package xyz.marsj.o2o.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.ProductSellDaily;

public interface ProductSellDailyMapper {
	//根据日期查询日销量统计表
	List<ProductSellDaily> queryProductSellDaily(@Param("productSellDailyCondition")ProductSellDaily productSellDailyCondition ,@Param("beginTime")Date beginTime,@Param("endTime")Date endTime);
	//统计日销量
	int insertProductSellDaily();
	int insertDefaultProductSellDaily();
}
