package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.ProductSellDaily;
import xyz.marsj.o2o.entity.Shop;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyMapperTest  {
	@Autowired
	private ProductSellDailyMapper productSellDailyMapper;
	@Test
	public void testInsertProductSellDaily(){
		int effectedNum = productSellDailyMapper.insertProductSellDaily();
		assertEquals(3, effectedNum);
	}

	@Test
	public void testInsertDefaultProductSellDaily(){
		productSellDailyMapper.insertDefaultProductSellDaily();
	}
	@Test
	public void testQueryProductSellDaily(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		
		cal.add(Calendar.DATE, -6);
		Date beginTime = cal.getTime();

		ProductSellDaily productSellDaily=new ProductSellDaily();
		Shop shop=new Shop();
		shop.setShopId(20L);
		productSellDaily.setShop(shop);
		List<ProductSellDaily> queryProductSellDailyList = productSellDailyMapper.queryProductSellDaily(productSellDaily, beginTime, endTime);
		assertEquals(8, queryProductSellDailyList.size());
	}
	
	
}
