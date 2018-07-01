package xyz.marsj.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.LocalAuthExecution;
import xyz.marsj.o2o.entity.LocalAuth;
import xyz.marsj.o2o.enums.LocalAuthStateEnum;
import xyz.marsj.o2o.exceptions.LocalAuthOperationException;
import xyz.marsj.o2o.mapper.LocalAuthMapper;
import xyz.marsj.o2o.service.ILocalAuthService;
import xyz.marsj.o2o.util.MD5;
@Service
public class LocalAuthServiceImpl implements ILocalAuthService{
	@Autowired
	private LocalAuthMapper localAuthMapper;
	@Override
	public LocalAuth getLocalByUserNameAndPSW(String userName, String password) {
		return localAuthMapper.queryLocalByUserNameAndPSW(userName, MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalByUserId(long userId) {
		return localAuthMapper.queryLocalByUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
		//判空
		if(localAuth==null||localAuth.getPassword()==null||localAuth.getUserName()==null||
				localAuth.getPersonInfo()==null||localAuth.getPersonInfo().getUserId()==null){
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		//只能绑定一个本地账号
		LocalAuth tempAuth = localAuthMapper.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
		if(tempAuth!=null){
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectedNum = localAuthMapper.insertLocalAuth(localAuth);
			if(effectedNum<0){
				throw new LocalAuthOperationException("账号绑定失败");
			}else{
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("insertLocalAuth error"+e.getMessage());
		}
	
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword)
			throws LocalAuthOperationException {
		if(userId!=null&&userName!=null&&password!=null&&newPassword!=null&&!password.equals(newPassword)){
		
			try {
				int effectedNum = localAuthMapper.updateLocalAuth(userId, userName,
						MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
				if(effectedNum<=0){
					throw new LocalAuthOperationException("更新密码失败");
				}else{
					return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new LocalAuthOperationException("updateLocalAuth error"+e.getMessage());
			}
		}else{
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}
	

}
