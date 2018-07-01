package xyz.marsj.o2o.mapper;

import xyz.marsj.o2o.entity.WechatAuth;

public interface WechatAuthMapper {
	WechatAuth queryWechatAuthById(String openId);
	int insertWechatAuth(WechatAuth wechatAuth);
}
