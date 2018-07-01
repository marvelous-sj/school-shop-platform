package xyz.marsj.o2o.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.Shop;

public interface ShopMapper {
	/**
	 * 分页查询，查询条件 店铺名(模糊)，所在区域，店铺状态，店铺类别，owner
	 * @param shopCondition 查询条件
	 * @param rowIndex 开始查询索引
	 * @param pageSize 查询条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	//查询信息总条数
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	//查询店铺
	Shop queryByShopId(long ShopId);
	//新增店铺
	int insertShop(Shop shop);
	//更新店铺
	int updateShop(Shop shop);
}
