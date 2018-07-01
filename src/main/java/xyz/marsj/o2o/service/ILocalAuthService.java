package xyz.marsj.o2o.service;

import xyz.marsj.o2o.dto.LocalAuthExecution;
import xyz.marsj.o2o.entity.LocalAuth;
import xyz.marsj.o2o.exceptions.LocalAuthOperationException;

public interface ILocalAuthService {
	//通过用户名和密码查询 用户
	LocalAuth getLocalByUserNameAndPSW(String userName,String password);
	//通过userId查询Id
	LocalAuth getLocalByUserId(long userId);
	//本地账号与微信账号的绑定
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)throws LocalAuthOperationException;
	//修改密码
	LocalAuthExecution modifyLocalAuth(Long userId,String userName,String password,String newPassword)throws LocalAuthOperationException;
}
