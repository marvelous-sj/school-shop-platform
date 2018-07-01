package xyz.marsj.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.dto.WechatAuthExecution;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.WechatAuth;
import xyz.marsj.o2o.enums.WechatAuthStateEnum;
import xyz.marsj.o2o.exceptions.WechatAuthOperationException;
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest{
	@Autowired
	private IWechatAuthService wechatAuthService;
	@Test
	public void testRegister() throws WechatAuthOperationException{
	WechatAuth wechatAuth=new WechatAuth();
	PersonInfo personInfo=new PersonInfo();
	personInfo.setName("测试");
	wechatAuth.setOpenId("ceshi");
	wechatAuth.setPersonInfo(personInfo);
	WechatAuthExecution we = wechatAuthService.register(wechatAuth);
	assertEquals(WechatAuthStateEnum.SUCCESS.getState(), we.getState());
	WechatAuth wa = wechatAuthService.getWechatAuthByOpenId("ceshi");
	System.out.println(wa.getPersonInfo().getName());
	}
	

	
}
