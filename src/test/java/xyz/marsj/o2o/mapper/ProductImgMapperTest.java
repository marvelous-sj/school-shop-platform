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

import xyz.marsj.o2o.entity.ProductImg;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductImgMapperTest {
	@Autowired
	private ProductImgMapper productImgMapper;
	@Test
	public void testInsertProduct(){
	List<ProductImg> productImgList=new ArrayList<ProductImg>();
		ProductImg pi1=new ProductImg();
		pi1.setImgAddr("imgAddr1");
		pi1.setImgDesc("imgDesc1");
		pi1.setCreateTime(new Date());
		pi1.setPriority(1);
		pi1.setProductId(28L);
		
		ProductImg pi2=new ProductImg();
		pi2.setImgAddr("imgAddr2");
		pi2.setImgDesc("imgDesc2");
		pi2.setCreateTime(new Date());
		pi2.setPriority(2);
		pi2.setProductId(28L);
		
		productImgList.add(pi1);
		productImgList.add(pi2);
		int effectedNum = productImgMapper.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
	}
	@Test
	public void testDeletProductByProductId(){
		int effectedNum = productImgMapper.deleteProductImgByProductId(28L);
		assertEquals(2,effectedNum);
	}
	
	@Test
	public void testQueryProductImgList(){
		 List<ProductImg> productImgList = productImgMapper.queryProductImgList(28L);
		assertEquals(2,productImgList.size());
	}
}
