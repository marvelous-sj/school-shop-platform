package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.WechatAuth;
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthMapperTest {
	@Autowired
	private WechatAuthMapper wechatAuthMapper;
	@Test
	public void insertWechatAuthTest(){
		PersonInfo personInfo=new PersonInfo();
		personInfo.setUserId(12L);
		personInfo.setEnableStatus(1);
		WechatAuth wechatAuth=new WechatAuth();
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId("zzz");
		wechatAuth.setCreateTime(new Date());
		int effectNum = wechatAuthMapper.insertWechatAuth(wechatAuth);
		assertEquals(1, effectNum);
	}
	
	
	@Test
	public void queryWechatAuthByIdTest(){
		WechatAuth wechatAuth = wechatAuthMapper.queryWechatAuthById("zzz");
		System.out.println(wechatAuth.getWechatAuthId());
	}
	
}
