package xyz.marsj.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.marsj.o2o.entity.ProductSellDaily;
import xyz.marsj.o2o.mapper.ProductSellDailyMapper;
import xyz.marsj.o2o.service.IProductSellDailyService;
@Service
public class ProductSellDailyServiceImpl implements IProductSellDailyService {
	@Autowired
	private ProductSellDailyMapper productSellDailyMapper;
	@Override
	public void dailyCalculate() {
		productSellDailyMapper.insertProductSellDaily();
		productSellDailyMapper.insertDefaultProductSellDaily();
	}
	@Override
	public List<ProductSellDaily> queryProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime,
			Date endTime) {
		return productSellDailyMapper.queryProductSellDaily(productSellDailyCondition, beginTime, endTime);
	}
}
