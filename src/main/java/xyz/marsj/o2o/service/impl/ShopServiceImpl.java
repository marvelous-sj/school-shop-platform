	package xyz.marsj.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.dto.ShopExecution;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.ShopAuthMap;
import xyz.marsj.o2o.enums.ShopStateEnum;
import xyz.marsj.o2o.exceptions.ShopOperationException;
import xyz.marsj.o2o.mapper.ShopAuthMapMapper;
import xyz.marsj.o2o.mapper.ShopMapper;
import xyz.marsj.o2o.service.IShopService;
import xyz.marsj.o2o.util.ImgUtil;
import xyz.marsj.o2o.util.PageUtil;
import xyz.marsj.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements IShopService {
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private ShopAuthMapMapper shopAuthMapMapper;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, ImgHolder thumbnail) {
		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 添加默认值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// insertShop
			int effectNum = shopMapper.insertShop(shop);
			if (effectNum <= 0) {
				throw new ShopOperationException("创建店铺失败");
			} else {
				if (thumbnail.getImg() != null) {
					try {
						// 存储图片
						addShopImg(shop, thumbnail);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					// 更新图片地址
					effectNum = shopMapper.updateShop(shop);
					if (effectNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
					ShopAuthMap shopAuthMap=new ShopAuthMap();
					shopAuthMap.setShop(shop);
					shopAuthMap.setTitle("店家本人");
					shopAuthMap.setTitleFlag(1);
					shopAuthMap.setEnableStatus(1);
					shopAuthMap.setCreateTime(new Date());
					shopAuthMap.setLastEditTime(new Date());
					shopAuthMap.setEmployee(shop.getOwner());
					try {
						int effectedNum = shopAuthMapMapper.insertShopAuthMap(shopAuthMap);
						if(effectedNum<=0) {
							throw new ShopOperationException("授权创建失败");
						}
					} catch (Exception e) {
						throw new ShopOperationException("授权创建失败");
					}
				
				}
			}

		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}

		return new ShopExecution(ShopStateEnum.CHECK);
	}

	private void addShopImg(Shop shop, ImgHolder thumbnail) {
		String basePath = PathUtil.getShopImgPath(shop.getShopId());
		String imgUrl = ImgUtil.generateThumbnail(thumbnail, basePath);
		shop.setShopImg(imgUrl);
	}

	@Override
	public Shop queryByShopId(long ShopId) {
		return shopMapper.queryByShopId(ShopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop,ImgHolder thumbnail)
			throws ShopOperationException {
		if(shop==null||shop.getShopId()==null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else{
			try {
				//判断是否需要修改图片
				if(thumbnail.getImg()!=null && thumbnail.getImgName()!=null &&!"".equals(thumbnail.getImgName())){
					//获取当前图片信息
					Shop tempShop= shopMapper.queryByShopId(shop.getShopId());
					if(tempShop.getShopImg()!=null){
						ImgUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop,thumbnail);
				}
				//更新店铺信息
					shop.setLastEditTime(new Date());
					int effectedNum = shopMapper.updateShop(shop);
					if(effectedNum<=0){
						return new ShopExecution(ShopStateEnum.INNER_ERROR);
					}else{
						//更新成功
						shop = shopMapper.queryByShopId(shop.getShopId());
						return new ShopExecution(ShopStateEnum.SUCCESS,shop);
					}
			} catch (Exception e) {
				throw new ShopOperationException("modifyShop error"+e.getMessage());
			}
		}
	}

	@Override
	public ShopExecution queryShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageUtil.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopMapper.queryShopList(shopCondition, rowIndex, pageSize);
		int count=shopMapper.queryShopCount(shopCondition);
		ShopExecution se=new ShopExecution();
		if(shopList!=null){
			se.setShopList(shopList);
			se.setCount(count);
		}else{
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
