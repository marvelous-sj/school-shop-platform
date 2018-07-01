package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.ProductCategory;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest   {
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
//	@Autowired
//	private IProductCategoryService productCategoryService;
//	@Test
//	public void testAreaQuery(){
//	  List<ProductCategory> queryProductCategoryList = productCategoryService.queryProductCategoryList(20L);
//		assertEquals(5, queryProductCategoryList.size());
//	}
	@Test
	public void testBatchAddProductCategory(){
		List<ProductCategory> list= new ArrayList<>();
		ProductCategory pc1=new ProductCategory();
		pc1.setProductCategoryName("t1");
		pc1.setPriority(1);
		pc1.setCreateTime(new Date());
		pc1.setShopId(15L);
		ProductCategory pc2=new ProductCategory();
		pc2.setProductCategoryName("t2");
		pc2.setPriority(2);
		pc2.setCreateTime(new Date());
		pc2.setShopId(15L);
		list.add(pc1);
		list.add(pc2);
		int effectedNum = productCategoryMapper.batchInsertProductCategory(list);
		assertEquals(2, effectedNum);
	}
	
	@Test
	public void testDeleteProductCategory(){
		long shopId=15L;
		List<ProductCategory> list = productCategoryMapper.queryProductCategoryList(shopId);
		for (ProductCategory productCategory : list) {
			if("t1".equals(productCategory.getProductCategoryName())||"t2".equals(productCategory.getProductCategoryName())){
				int effectedNum = productCategoryMapper.delectProductCategory(productCategory.getProductCategoryId(), shopId);
				assertEquals(1, effectedNum);
			}
		}
	}
}
