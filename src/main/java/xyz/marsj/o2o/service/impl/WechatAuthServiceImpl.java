package xyz.marsj.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.WechatAuthExecution;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.WechatAuth;
import xyz.marsj.o2o.enums.WechatAuthStateEnum;
import xyz.marsj.o2o.exceptions.WechatAuthOperationException;
import xyz.marsj.o2o.mapper.PersonInfoMapper;
import xyz.marsj.o2o.mapper.WechatAuthMapper;
import xyz.marsj.o2o.service.IWechatAuthService;
@Service
public class WechatAuthServiceImpl implements IWechatAuthService {
	@Autowired
	private WechatAuthMapper wechatAuthMapper;
	@Autowired
	private PersonInfoMapper personInfoMapper;
	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthMapper.queryWechatAuthById(openId);
	}

	@Override
	@Transactional
	public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
		if(wechatAuth==null||wechatAuth.getOpenId()==null){
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}
		try {
			//第一次注册
			if(wechatAuth.getPersonInfo()!=null&&wechatAuth.getUserId()==null){
				try {
					wechatAuth.setCreateTime(new Date());
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					int effectedNum = personInfoMapper.insertPersonInfo(personInfo);
					wechatAuth.setPersonInfo(personInfo);
					if(effectedNum<0){
						throw new WechatAuthOperationException("添加用户信息失败");
					}
				} catch (Exception e) {
					throw new WechatAuthOperationException("insertPersonInfo error:"+e.getMessage());
				}
				}
				int effectedNum = wechatAuthMapper.insertWechatAuth(wechatAuth);
				if(effectedNum<0){
					throw new WechatAuthOperationException("账号创建失败");
				}else{
					return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,wechatAuth);
				}
			
		} catch (Exception e) {
			throw new WechatAuthOperationException("insertPersonInfo error:"+e.getMessage());
		}
	}

}
