package xyz.marsj.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.UserProductMapExecution;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.UserProductMap;
import xyz.marsj.o2o.entity.UserShopMap;
import xyz.marsj.o2o.enums.UserProductMapStateEnum;
import xyz.marsj.o2o.exceptions.UserProductMapOperationException;
import xyz.marsj.o2o.mapper.UserProductMapMapper;
import xyz.marsj.o2o.mapper.UserShopMapMapper;
import xyz.marsj.o2o.service.IUserProductMapService;
import xyz.marsj.o2o.util.PageUtil;
@Service
public class UserProductMapServiceImpl implements IUserProductMapService {
	@Autowired
	private UserProductMapMapper userProductMapMapper;
	@Autowired 
	private UserShopMapMapper userShopMapMapper;
	@Override
	public UserProductMapExecution listUserProductMap(UserProductMap userProductMapCondition, Integer pageIndex,
			Integer pageSize) {
		if(userProductMapCondition!=null&&pageIndex!=null&&pageSize!=null) {
			int rowIndex=PageUtil.calculateRowIndex(pageIndex, pageSize);
			List<UserProductMap> userProductMapList = userProductMapMapper.queryUserProductMapList(userProductMapCondition, rowIndex, pageSize);
			int count = userProductMapMapper.queryUserProductMapCount(userProductMapCondition);
			UserProductMapExecution upme=new UserProductMapExecution();
			upme.setCount(count);
			upme.setUserProductMapList(userProductMapList);
			return upme;
		}else {
			return null;
		}
	}
	@Override
	@Transactional
	public UserProductMapExecution addUserProductMap(UserProductMap userProductMap)
			throws UserProductMapOperationException {
		//空值判断(用户Id和商店Id和操作员Id)
		if(userProductMap!=null&&userProductMap.getUser().getUserId()!=null&&userProductMap.getShop().getShopId()!=null
				&&userProductMap.getOperator().getUserId()!=null) {
			try {
				//设置默认值
				userProductMap.setCreateTime(new Date());
				//添加消费记录
				int effectedNum = userProductMapMapper.insertUserProductMap(userProductMap);
				if(effectedNum<=0) {
					throw new UserProductMapOperationException("添加消费记录失败");
				}
				//消费能积分则跟新积分
				if(userProductMap.getPoint()!=null&&userProductMap.getPoint()>0) {
					UserShopMap userShopMap = userShopMapMapper.queryUserShopMap(userProductMap.getUser().getUserId(), userProductMap.getShop().getShopId());
					if(userShopMap!=null&&userShopMap.getUserShopId()!=null) {
						//已有消费记录
						userShopMap.setPoint(userShopMap.getPoint()+userProductMap.getPoint());
						effectedNum = userShopMapMapper.updateUserShopMap(userShopMap);
						if(effectedNum<=0) {
							throw new UserProductMapOperationException("更新积分记录失败");
						}
					}else {
						//第一次消费，添加记录(初始化积分)
						 userShopMap=new UserShopMap();
						 PersonInfo user=new PersonInfo();
						 Shop shop=new Shop();
						 user.setUserId(userProductMap.getUser().getUserId());
						 shop.setShopId(userProductMap.getShop().getShopId());
						 userShopMap.setShop(shop);
						 userShopMap.setUser(user);
						 userShopMap.setCreateTime(new Date());
						 userShopMap.setPoint(userProductMap.getPoint());
						effectedNum = userShopMapMapper.insertUserShopMap(userShopMap);
						if(effectedNum<=0) {
							throw new UserProductMapOperationException("创建积分记录失败");
						}
					}
					
				}
				return new UserProductMapExecution(UserProductMapStateEnum.SUCCESS,userProductMap);
			} catch (Exception e) {
				throw new UserProductMapOperationException("添加信息失败"+e.getMessage());
			}
			
		}else {
			
			return new UserProductMapExecution(UserProductMapStateEnum.FAIL);
		}
		
	}
	

}
