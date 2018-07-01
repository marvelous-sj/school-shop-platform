package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.Award;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.UserAwardMap;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAwardMapMapperTest  {
	@Autowired
	private UserAwardMapMapper userAwardMap;
	@Test
	public void testInsertUserAwardMap(){
		Award award=new Award();
		award.setAwardId(2L);
		Shop shop =new Shop();
		shop.setShopId(20L);
		PersonInfo cust=new PersonInfo();
		cust.setUserId(8L);
		PersonInfo operator=new PersonInfo();
		operator.setUserId(13L);
		UserAwardMap u=new UserAwardMap();
		u.setAward(award);
		u.setOperator(operator);
		u.setShop(shop);
		u.setUser(cust);
		u.setExpireTime(new Date());
		u.setCreateTime(new Date());
		u.setUsedStatus(0);
		int effectedNum = userAwardMap.insertUserAwardMap(u);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testUpdateUserAwardMap(){
		UserAwardMap u=new UserAwardMap();
		u.setUserAwardId(2L);
		PersonInfo cust=new PersonInfo();
		cust.setUserId(13L);
		u.setUsedStatus(1);
		u.setUser(cust);
		int effectedNum =userAwardMap.updateUserAwardMap(u);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQueryUserAwardMapById(){
	 UserAwardMap queryUserAwardMapById = userAwardMap.queryUserAwardMapById(3L);
		System.out.println(queryUserAwardMapById.getUser().getName());
	}
	
	@Test
	public void testQueryUserAwardMapList(){
		UserAwardMap userAwardMapCondition=new UserAwardMap();
		//userAwardMapCondition.setUsedStatus(1);
		PersonInfo cust=new PersonInfo();
		cust.setUserId(8L);
//		cust.setName("斩");
		userAwardMapCondition.setUser(cust);
//		Shop shop =new Shop();
//		shop.setShopId(20L);
//		userAwardMapCondition.setShop(shop);
//		Award award=new Award();
//		award.setAwardName("2");
//		userAwardMapCondition.setAward(award);
		List<UserAwardMap> list = userAwardMap.queryUserAwardMapList(userAwardMapCondition, 0, 5);
		assertEquals(2, list.size());
	}
	@Test
	public void testQueryUserAwardMapCount(){
		UserAwardMap userAwardMapCondition=new UserAwardMap();
		int count = userAwardMap.queryUserAwardMapCount(userAwardMapCondition);
		System.out.println(count);
	}
	
	
}
