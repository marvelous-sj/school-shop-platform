package xyz.marsj.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.ShopAuthMapExecution;
import xyz.marsj.o2o.entity.ShopAuthMap;
import xyz.marsj.o2o.enums.ShopAuthMapStateEnum;
import xyz.marsj.o2o.exceptions.ShopAuthMapOperationException;
import xyz.marsj.o2o.mapper.ShopAuthMapMapper;
import xyz.marsj.o2o.service.IShopAuthMapService;
import xyz.marsj.o2o.util.PageUtil;
@Service
public class ShopAuthMapServiceImpl implements IShopAuthMapService {
	@Autowired
	private ShopAuthMapMapper shopAuthMapMapper;
	@Override
	public ShopAuthMapExecution listShopAuthMapListById(Long shopId, Integer pageIndex, Integer pageSize) {
		if(shopId!=null&&pageIndex!=null&&pageSize!=null) {
			try {
				int rowIndex = PageUtil.calculateRowIndex(pageIndex, pageSize);
				List<ShopAuthMap> shopAuthMapList = shopAuthMapMapper.queryShopAuthMapList(shopId, rowIndex, pageSize);
				int count = shopAuthMapMapper.queryShopAuthMapCount(shopId);
				ShopAuthMapExecution se=new ShopAuthMapExecution();
				se.setCount(count);
				se.setShopAuthMapList(shopAuthMapList);
				return se;
			} catch (Exception e) {
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
			}
		}else {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOP_ID);
		}
	}

	@Override
	public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
		return shopAuthMapMapper.queryShopAuthMapById(shopAuthId);
	}

	@Override
	@Transactional
	public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
		if(shopAuthMap!=null&&shopAuthMap.getShop()!=null&&shopAuthMap.getShop().getShopId()!=null&&
				shopAuthMap.getEmployee()!=null&&shopAuthMap.getEmployee().getUserId()!=null) {
			shopAuthMap.setCreateTime(new Date());
			shopAuthMap.setLastEditTime(new Date());
			shopAuthMap.setEnableStatus(1);
			shopAuthMap.setTitleFlag(1);
			try {
				int effectedNum = shopAuthMapMapper.insertShopAuthMap(shopAuthMap);
				if(effectedNum<=0) {
					throw new ShopAuthMapOperationException("添加授权失败");
				}else {
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
				}
			} catch (Exception e) {
				throw new ShopAuthMapOperationException("添加授权失败"+e.getMessage());
			}
		}else {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
		}
		
	}

	@Override
	@Transactional
	public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
		if(shopAuthMap==null||shopAuthMap.getShopAuthId()==null) {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
		}
			shopAuthMap.setLastEditTime(new Date());
			try {
				int effectedNum = shopAuthMapMapper.updateShopAuthMap(shopAuthMap);
				if(effectedNum<=0) {
					throw new ShopAuthMapOperationException("修改授权失败");
				}else {
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
				}
			} catch (Exception e) {
				throw new ShopAuthMapOperationException("修改授权失败"+e.getMessage());
			}
	}

}
