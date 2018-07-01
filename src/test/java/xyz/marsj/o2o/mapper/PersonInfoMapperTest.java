package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.PersonInfo;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoMapperTest {
	@Autowired
	private PersonInfoMapper personInfoMapper;
	@Test
	public void insertPersonInfoTest(){
		PersonInfo personInfo=new PersonInfo();
		personInfo.setName("沈杰");
		personInfo.setBirthday(new Date());
		personInfo.setGender("1");
		personInfo.setCreateTime(new Date());
		personInfo.setLastEditTime(new Date());
		personInfo.setEmail("446454384@163.com");
		personInfo.setPhone("177");
		personInfo.setEnableStatus(1);
		int effectNum = personInfoMapper.insertPersonInfo(personInfo);
		assertEquals(1, effectNum);
	}
	
	
	@Test
	public void queryPersonInfoByIdTest(){
		PersonInfo personInfo = personInfoMapper.queryPersonInfoById(12L);
		assertEquals("沈杰", personInfo.getName());
	}
	
}
