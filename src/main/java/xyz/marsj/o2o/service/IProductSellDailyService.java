package xyz.marsj.o2o.service;

import java.util.Date;
import java.util.List;

import xyz.marsj.o2o.entity.ProductSellDaily;

public interface IProductSellDailyService {
	void dailyCalculate();
	List<ProductSellDaily> queryProductSellDaily(ProductSellDaily productSellDailyCondition ,Date beginTime,Date endTime);
}
