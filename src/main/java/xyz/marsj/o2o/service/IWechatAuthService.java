package xyz.marsj.o2o.service;

import xyz.marsj.o2o.dto.WechatAuthExecution;
import xyz.marsj.o2o.entity.WechatAuth;
import xyz.marsj.o2o.exceptions.WechatAuthOperationException;

public interface IWechatAuthService {
	WechatAuth getWechatAuthByOpenId(String openId);
	WechatAuthExecution register(WechatAuth wechatAuth)throws WechatAuthOperationException;
}
