package xyz.marsj.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.marsj.o2o.dto.UserShopMapExecution;
import xyz.marsj.o2o.entity.UserShopMap;
import xyz.marsj.o2o.mapper.UserShopMapMapper;
import xyz.marsj.o2o.service.IUserShopMapService;
import xyz.marsj.o2o.util.PageUtil;
@Service
public class UserShopMapServiceImpl implements IUserShopMapService {

	@Autowired
	private UserShopMapMapper userShopMapMapper;
	@Override
	public UserShopMapExecution getUserShopMapList(UserShopMap userShopMap, int pageIndex, int pageSize) {
		if(pageIndex!=-1&&pageSize!=-1&&userShopMap!=null) {
			int rowIndex = PageUtil.calculateRowIndex(pageIndex, pageSize);
			//集合
			List<UserShopMap> userShopMapList = userShopMapMapper.queryUserShopMapList(userShopMap, rowIndex, pageSize);
			//总数
			int count = userShopMapMapper.queryUserShopMapCount(userShopMap);
			UserShopMapExecution use=new UserShopMapExecution();
			use.setCount(count);
			use.setUserShopMapList(userShopMapList);
			return use;
		}else {
			return null;
		}
	}

	@Override
	public UserShopMap getUserShopMap(Long userId, Long shopId) {
		return userShopMapMapper.queryUserShopMap(userId, shopId);
	}

}
