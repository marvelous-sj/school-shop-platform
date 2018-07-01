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
import xyz.marsj.o2o.entity.Product;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.UserShopMap;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserShopMapMapperTest  {
	@Autowired
	private UserShopMapMapper userShopMap;
	@Test
	public void testInsertUserShopMap(){
		Shop shop=new Shop();
		shop.setShopId(15L);
		shop.setShopName("shop1");
		PersonInfo cust=new PersonInfo();
		cust.setUserId(13L);
		cust.setName("user1");
		Product product=new Product();
		product.setProductId(14L);
		UserShopMap u=new UserShopMap();
		u.setShop(shop);
//		u.setProduct(product);
		u.setUser(cust);
		u.setCreateTime(new Date());
		u.setPoint(11);
		int effectedNum = userShopMap.insertUserShopMap(u);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testUpdateUserShopMap(){
		UserShopMap u=new UserShopMap();
		Shop shop =new Shop();
		shop.setShopId(15L);
		PersonInfo cust=new PersonInfo();
		cust.setUserId(13L);
		u.setShop(shop);
		u.setPoint(15);
		u.setUser(cust);
		int effectedNum =userShopMap.updateUserShopMap(u);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQueryUserShopMapById(){
	 UserShopMap queryUserShopMapById = userShopMap.queryUserShopMap(8L,20L);
		System.out.println(queryUserShopMapById.getUser().getName());
	}
	
	@Test
	public void testQueryUserShopMapList(){
		UserShopMap userShopMapCondition=new UserShopMap();
		PersonInfo o=new PersonInfo();
		o.setUserId(11L);
		userShopMapCondition.setOperator(o);
//		Shop shop =new Shop();
//		shop.setShopId(20L);
//		userShopMapCondition.setShop(shop);
//		Shop Shop=new Shop();
//		Shop.setShopName("车");
//		userShopMapCondition.setShop(Shop);
		List<UserShopMap> list = userShopMap.queryUserShopMapList(userShopMapCondition, 0, 5);
		assertEquals(2, list.size());
	}
	@Test
	public void testQueryUserShopMapCount(){
		UserShopMap userShopMapCondition=new UserShopMap();
		int count = userShopMap.queryUserShopMapCount(userShopMapCondition);
		System.out.println(count);
	}
	
	
}
