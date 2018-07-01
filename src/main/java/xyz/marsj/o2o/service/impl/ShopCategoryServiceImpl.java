package xyz.marsj.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.marsj.o2o.entity.ShopCategory;
import xyz.marsj.o2o.mapper.ShopCategoryMapper;
import xyz.marsj.o2o.service.IShopCategoryService;
@Service
public class ShopCategoryServiceImpl implements IShopCategoryService{
	@Autowired
	private ShopCategoryMapper shopcategoryMapper;
	

	/*
	 * 	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Override
	@Transactional
	public List<ShopCategory> queryShopCategoryList(ShopCategory shopCategoryCondition) {
		String key=SHOPCATELISTKEY;
		List<ShopCategory> shopCategoryList =null;
		ObjectMapper mapper=new ObjectMapper();
		if(shopCategoryCondition==null){
			key=key+"_allfirstlevel";
		}else if(shopCategoryCondition!=null&&shopCategoryCondition.getParentId()!=null){
			key=key+"_parent"+shopCategoryCondition.getParentId();
		}else {
			key=key+"_allsecondlevel";
		}
		
		//redis不存在arealist信息
		if(!jedisKeys.exists(key)){
			shopCategoryList = shopcategoryMapper.queryShopCategory(shopCategoryCondition);
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(shopCategoryList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key,jsonString);
		}else{
			//redis存在arealistx信息
			//直接取
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				shopCategoryList = mapper.readValue(jsonString, javaType);
			} catch (Exception e) {
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			}
			
		}
		return shopCategoryList;
	}
	

	*/
	@Override
	public List<ShopCategory> queryShopCategoryList(ShopCategory shopCategoryCondition) {
		List<ShopCategory> shopCategoryList =null;
		shopCategoryList = shopcategoryMapper.queryShopCategory(shopCategoryCondition);
		return shopCategoryList;
	}
}
