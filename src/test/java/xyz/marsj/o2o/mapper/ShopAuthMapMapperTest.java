package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.ShopAuthMap;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopAuthMapMapperTest  {
	@Autowired
	private ShopAuthMapMapper shopAuthMap;
	@Test
	public void testInsertShopAuthMap(){
		Shop shop =new Shop();
		shop.setShopId(20L);
		PersonInfo employee=new PersonInfo();
		employee.setUserId(8L);
		ShopAuthMap u=new ShopAuthMap();
		u.setEmployee(employee);
		u.setShop(shop);
		u.setEnableStatus(1);
		u.setTitle("测试");
		u.setTitleFlag(11);
		u.setCreateTime(new Date());
		
		int effectedNum = shopAuthMap.insertShopAuthMap(u);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testUpdateShopAuthMap(){
		ShopAuthMap u=new ShopAuthMap();
		u.setShopAuthId(25L);
		u.setLastEditTime(new Date());
		u.setEnableStatus(0);
		int effectedNum =shopAuthMap.updateShopAuthMap(u);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQueryShopAuthMapById(){
	 ShopAuthMap queryShopAuthMapById = shopAuthMap.queryShopAuthMapById(20L);
		System.out.println(queryShopAuthMapById.getEmployee().getName());
	}
	
	@Test
	public void testQueryShopAuthMapList(){
		List<ShopAuthMap> list = shopAuthMap.queryShopAuthMapList(20L, 0, 5);
		System.out.println(list.get(0).getEmployee().getName());
	}
	@Test
	public void testQueryShopAuthMapCount(){
		int count = shopAuthMap.queryShopAuthMapCount(20L);
		System.out.println(count);
	}
	
	
}
