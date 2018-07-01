package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.Product;
import xyz.marsj.o2o.entity.ProductCategory;
import xyz.marsj.o2o.entity.Shop;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTest {
	@Autowired
	private ProductMapper productMapper;
	@Test
	public void testInsertProduct(){
		Shop shop=new Shop();
		shop.setShopId(15L);
		ProductCategory pc=new ProductCategory();
		pc.setProductCategoryId(9L);
		Product product=new Product();
		product.setProductName("test1");
		product.setCreateTime(new Date());
		product.setPriority(50);
		product.setEnableStatus(1);
		product.setShop(shop);
		product.setProductCategory(pc);
		
		int effectedNum = productMapper.insertProduct(product);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testQueryProductById(){
		Product product = productMapper.queryProductById(10L);
		assertEquals(3,  product.getProductImgList().size());
	}
	@Test
	public void testUpdateProduct(){
		Shop shop=new Shop();
		shop.setShopId(15L);
		ProductCategory pc=new ProductCategory();
		pc.setProductCategoryId(10L);
		Product product=new Product();
		product.setProductName("test1");
		product.setCreateTime(new Date());
		product.setPriority(49);
		product.setEnableStatus(1);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setImgAddr("test");
		product.setProductDesc("t");
		product.setProductId(29L);
		int effectedNum = productMapper.updateProduct(product);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQueryProductCount(){
		Product product =new Product();
		product.setEnableStatus(1);
		int count = productMapper.queryProductCount(product);
		assertEquals(19, count);
				
	}
	
	@Test
	public void testQueryProductList(){
		Product product =new Product();
		product.setEnableStatus(1);
		List<Product> list = productMapper.queryProductList(product, 0, 6);
		assertEquals(6, list.size());
	}
	@Test
	public void testUpdateProductCategoryToNULL(){
		int effectedNum = productMapper.updateProductCategoryToNULL(54);
		assertEquals(5, effectedNum);
	}
}
