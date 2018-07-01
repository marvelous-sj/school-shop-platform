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
import xyz.marsj.o2o.entity.UserProductMap;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProductMapMapperTest  {
	@Autowired
	private UserProductMapMapper userProductMap;
	@Test
	public void testInsertUserProductMap(){
		Product product=new Product();
		product.setProductId(12L);
		product.setProductName("1");
		Shop shop =new Shop();
		shop.setShopId(15L);
		PersonInfo cust=new PersonInfo();
		cust.setUserId(13L);
		cust.setName("s");
		PersonInfo operator=new PersonInfo();
		operator.setUserId(13L);
		UserProductMap u=new UserProductMap();
		u.setProduct(product);
		u.setOperator(operator);
		u.setShop(shop);
		u.setUser(cust);

		u.setCreateTime(new Date());
		u.setPoint(1);
		int effectedNum = userProductMap.insertUserProductMap(u);
		assertEquals(1, effectedNum);
	}


	
	@Test
	public void testQueryUserProductMapList(){
		UserProductMap userProductMapCondition=new UserProductMap();
		
//		PersonInfo cust=new PersonInfo();
//		cust.setUserId(8L);
//		cust.setName("斩");
//		userProductMapCondition.setUser(cust);
//		Shop shop =new Shop();
//		shop.setShopId(20L);
//		userProductMapCondition.setShop(shop);
//		Product Product=new Product();
//		Product.setProductName("冰");
//		userProductMapCondition.setProduct(Product);
		List<UserProductMap> list = userProductMap.queryUserProductMapList(userProductMapCondition, 0, 10);
		assertEquals(2, list.size());
	}
	@Test
	public void testQueryUserProductMapCount(){
		UserProductMap userProductMapCondition=new UserProductMap();
		int count = userProductMap.queryUserProductMapCount(userProductMapCondition);
		System.out.println(count);
	}
	
	
}
