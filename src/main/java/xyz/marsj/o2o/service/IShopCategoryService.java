package xyz.marsj.o2o.service;

import java.util.List;

import xyz.marsj.o2o.entity.ShopCategory;

public interface IShopCategoryService {
	public static final String SHOPCATELISTKEY="shopcategorylist";
	List<ShopCategory> queryShopCategoryList(ShopCategory shopCategoryCondition);
}
