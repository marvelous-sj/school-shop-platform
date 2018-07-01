package xyz.marsj.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.UserAwardMapExecution;
import xyz.marsj.o2o.entity.UserAwardMap;
import xyz.marsj.o2o.entity.UserShopMap;
import xyz.marsj.o2o.enums.UserAwardMapStateEnum;
import xyz.marsj.o2o.exceptions.UserAwardMapOperationException;
import xyz.marsj.o2o.mapper.UserAwardMapMapper;
import xyz.marsj.o2o.mapper.UserShopMapMapper;
import xyz.marsj.o2o.service.IUserAwardMapService;
import xyz.marsj.o2o.util.PageUtil;
@Service
public class UserAwardMapServiceImpl implements IUserAwardMapService {

	@Autowired
	private UserAwardMapMapper userAwardMapMapper;
	@Autowired
	private UserShopMapMapper UserShopMapMapper;
	@Override
	public UserAwardMapExecution getUserAwardMapList(UserAwardMap userAwardMapCondition, int pageIndex, int pageSize) {
		if(pageIndex!=-1&&pageSize!=-1&&userAwardMapCondition!=null) {
			int rowIndex = PageUtil.calculateRowIndex(pageIndex, pageSize);
			//集合
			List<UserAwardMap> userAwardMapList = userAwardMapMapper.queryUserAwardMapList(userAwardMapCondition, rowIndex, pageSize);
			//总数
			int count = userAwardMapMapper.queryUserAwardMapCount(userAwardMapCondition);
			UserAwardMapExecution uae=new UserAwardMapExecution();
			uae.setCount(count);
			uae.setUserAwardMapList(userAwardMapList);
			return uae;
		}else {
			return null;
		}
	}
	@Override
	public UserAwardMap getUserAwardMapById(long userAwardMapId) {
		
		return userAwardMapMapper.queryUserAwardMapById(userAwardMapId);
	}
	@Override
	public UserAwardMap getUserAwardMapByIdFrontend(long userAwardMapId) {
		
		return userAwardMapMapper.queryUserAwardMapByIdFrontend(userAwardMapId);
	}
	@Override
	@Transactional
	public UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap) {
		if(userAwardMap!=null&&userAwardMap.getUser()!=null&&userAwardMap.getAward()!=null&&userAwardMap.getShop()!=null&&
				userAwardMap.getUser().getUserId()!=null&&userAwardMap.getAward().getAwardId()!=null&&userAwardMap.getShop().getShopId()!=null) {
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setUsedStatus(0);
			userAwardMap.setPoint(userAwardMap.getAward().getPoint());
			try {
				//消耗积分为0的，设置积分异常
				if(userAwardMap.getPoint()!=null&&userAwardMap.getPoint()>0) {
				UserShopMap userShopMap = UserShopMapMapper.queryUserShopMap(userAwardMap.getUser().getUserId(), userAwardMap.getShop().getShopId());
				//实际积分大于等于消耗积分
				if(userShopMap!=null&&userShopMap.getPoint()>=userAwardMap.getPoint()) {
					userShopMap.setPoint(userShopMap.getPoint()-userAwardMap.getPoint());
					int effectedNum = UserShopMapMapper.updateUserShopMap(userShopMap);
					if(effectedNum<=0) {
						throw new UserAwardMapOperationException("更新积分失败");	
						}
				}else {
					throw new UserAwardMapOperationException("积分不足，无法兑换");	
				}
				
				}else{
					throw new UserAwardMapOperationException("店铺积分系统异常");	
				}
				
				//添加兑换记录
				int effectedNum=userAwardMapMapper.insertUserAwardMap(userAwardMap);
				if(effectedNum<0) {
					throw new UserAwardMapOperationException("领取奖励失败");
				}
				return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
			} catch (Exception e) {
				throw new UserAwardMapOperationException("领取奖励失败"+e.getMessage());
			}
			
		}else {
			return new UserAwardMapExecution(UserAwardMapStateEnum.FAIL);
		}
		
	}
	@Override
	public UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardMapCondition, Integer pageIndex,
			Integer pageSize) {
		if(userAwardMapCondition!=null&&pageIndex!=null&&pageSize!=null) {
			int rowIndex=PageUtil.calculateRowIndex(pageIndex, pageSize);
			//前端奖品兑换展示列表
			 List<UserAwardMap> userAwardMapList = userAwardMapMapper.queryUserAwardMapListFrontend(userAwardMapCondition, rowIndex, pageSize);
			int count = userAwardMapMapper.queryUserAwardMapCountFrontend(userAwardMapCondition);
			UserAwardMapExecution uae = new UserAwardMapExecution();
			uae.setCount(count);
			uae.setUserAwardMapList(userAwardMapList);
			return uae;
		}else {
			return null;
		}
	}
	@Override
	@Transactional
	public UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap) {
		if(userAwardMap!=null&&userAwardMap.getUserAwardId()!=null&&userAwardMap.getUsedStatus()!=null) {
			try {
				int effectedNum = userAwardMapMapper.updateUserAwardMap(userAwardMap);
				if(effectedNum<=0) {
					return new UserAwardMapExecution(UserAwardMapStateEnum.ERROR);
				}else {
					return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS,userAwardMap);
				}
			} catch (Exception e) {
				throw new  UserAwardMapOperationException("modify UserAwardMap error"+e.getMessage());
			}
		}else {
			return new UserAwardMapExecution(UserAwardMapStateEnum.FAIL);
		}
		
	}



}
