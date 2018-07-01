package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.Area;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.ShopCategory;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopMapperTest  {
	@Autowired
	private ShopMapper shopMapper;
	@Test
	public void testQueryShopList(){
		Shop shopCondition=new Shop();
		PersonInfo owner=new PersonInfo();
		owner.setUserId(8L);
		shopCondition.setOwner(owner);
//		ShopCategory parentCategory =new ShopCategory();
//		parentCategory.setShopCategoryId(10L);
//		shopCondition.setParentCategory(parentCategory);
		shopCondition.setShopName("色");
		shopCondition.setEnableStatus(1);
		List<Shop> list = shopMapper.queryShopList(shopCondition, 0, 5);
		System.out.println(list.size());
	}
	
	@Test
	public void testQueryShopListAndCount(){
		Shop shopCondition=new Shop();
		PersonInfo owner=new PersonInfo();
		owner.setUserId(8L);
		shopCondition.setOwner(owner);
		List<Shop> list = shopMapper.queryShopList(shopCondition, 0, 6);
		System.out.println("查询条数"+list.size());
		int count = shopMapper.queryShopCount(shopCondition);
		System.out.println("总条数"+count);
		
		ShopCategory shopCategory =new ShopCategory();
		shopCategory.setShopCategoryId(22L);
		shopCondition.setShopCategory(shopCategory);
		list = shopMapper.queryShopList(shopCondition, 0, 1);
		System.out.println("new查询条数"+list.size());
		count = shopMapper.queryShopCount(shopCondition);
		System.out.println("new总条数"+count);
		
		
	}
	
	@Test
	public void testQueryShopById(){
		long shopId=17;
		Shop result = shopMapper.queryByShopId(shopId);
		System.out.println(result.getArea().getAreaName());
		System.out.println(result.getShopName());
		System.out.println(result.getShopCategory().getShopCategoryName());
		
	}
	
	@Test
	public void testInsertShop(){
		//瞎搞 哈哈
		//List<ShopAuthMap> list=new ArrayList<ShopAuthMap>();
		Shop shop=new Shop();
		PersonInfo owner=new PersonInfo();
		//ShopAuthMap sap=new ShopAuthMap();
		
		Area area=new Area();
		ShopCategory shopCategory=new ShopCategory();
		owner.setUserId(8L);
		area.setAreaId(3L);
		shopCategory.setShopCategoryId(11L);
		//sap.setEmployee(owner);
		//list.add(sap);
		//shop.setStaffList(list);
		
		
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("test");
		shop.setEnableStatus(1);
		int insertShop = shopMapper.insertShop(shop);
		assertEquals(1, insertShop);
	}
	
	@Test
	public void testUpateShop(){
		Shop shop=new Shop();
		shop.setShopId(28L);
		shop.setShopName("测试名");
		shop.setPhone("1234561");
		shop.setEnableStatus(1);
		int updateShop = shopMapper.updateShop(shop);
		assertEquals(1, updateShop);
	}
}
