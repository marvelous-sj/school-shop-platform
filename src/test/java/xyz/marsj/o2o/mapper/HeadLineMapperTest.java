package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.HeadLine;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineMapperTest   {
	@Autowired
	private HeadLineMapper headLineMapper;
	@Test
	public void testQueryHeadLineList(){
		List<HeadLine> list=headLineMapper.queryHeadLineList(new HeadLine());
		assertEquals(4, list.size());
	}
}
