package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.ShopCategory;
@RunWith(SpringRunner.class)
@SpringBootTest
public class shopCategoryMapperTest  {
	@Autowired
	private ShopCategoryMapper shopCategoryMapper;
	@Test
	public void testQueryShopCategory(){
//		ShopCategory testCategory=new ShopCategory();
//		//ShopCategory parentCategory=new ShopCategory();
//		testCategory.setParentId(10L);
//	
//		List<ShopCategory> list = shopCategoryMapper.queryShopCategory(testCategory);
//		assertEquals(2, list.size());
		List<ShopCategory> list = shopCategoryMapper.queryShopCategory(null);
		assertEquals(6, list.size());
		
	} 
}
