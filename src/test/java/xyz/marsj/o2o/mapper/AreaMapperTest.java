package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.Area;
import xyz.marsj.o2o.mapper.AreaMapper;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaMapperTest  {
	@Autowired
	private AreaMapper areaMapper;
	@Test
	public void testAreaQuery(){
		List<Area> queryArea = areaMapper.queryArea();
		assertEquals(4, queryArea.size());
	}
}
