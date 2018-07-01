package xyz.marsj.o2o.controller.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xyz.marsj.o2o.dto.ShopExecution;
import xyz.marsj.o2o.entity.Area;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.ShopCategory;
import xyz.marsj.o2o.service.IAreaService;
import xyz.marsj.o2o.service.IShopCategoryService;
import xyz.marsj.o2o.service.IShopService;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
@RestController
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private IShopCategoryService shopCategoryService;
	@Autowired
	private IShopService shopService;
	@Autowired
	private IAreaService areaService;
	@RequestMapping(value="/listshopspageinfo",method=RequestMethod.GET)
	private Map<String,Object> listShopsPageInfo(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		long parentId=HttpServletRequestUtil.getLong(request,"parentId");
		List<ShopCategory> shopCategoryList=null;
		if(parentId!=-1L){
			try {
				//查询和parentId相对应的二级商店目录
				ShopCategory shopCategory=new ShopCategory();
				shopCategory.setParentId(parentId);
				shopCategoryList=shopCategoryService.queryShopCategoryList(shopCategory);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else{
			try {
				//前段点击全部商店或不传入parentId，查询所有一级商店目录
				shopCategoryList=shopCategoryService.queryShopCategoryList(null);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Area> areaList=null;
		try {
			areaList = areaService.getAreaList();
			modelMap.put("success", true);
			modelMap.put("areaList", areaList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
		
	}
	
	
	@RequestMapping(value="/listshops",method=RequestMethod.GET)
	private Map<String,Object> listShops(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
		if(pageIndex>-1&&pageSize>-1){
			//一级商店目录下的商店
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			//二级商店目录下的商店
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			//所在区域id
			long areaId=HttpServletRequestUtil.getLong(request, "areaId");
			//根据输入商店名模糊查询
			String shopName=HttpServletRequestUtil.getString(request, "shopName");
			
			Shop shopCondition=compactShopCondition(parentId,shopCategoryId,areaId,shopName);
			ShopExecution se = shopService.queryShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("count", se.getCount());
			modelMap.put("shopList",se.getShopList());
			
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex and pageSize is empty");
		}
		return modelMap;
	}


	private Shop compactShopCondition(long parentId, long shopCategoryId, long areaId, String shopName) {
		Shop shopCondition=new Shop();
		if(parentId!=-1L){
			ShopCategory parentCategory=new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			shopCondition.setParentCategory(parentCategory);
		}
		if(shopCategoryId!=-1L){
			ShopCategory shopCategory=new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setParentCategory(shopCategory);
		}
		if(areaId!=-1L){
			Area area=new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}
		if(shopName!=null){
			shopCondition.setShopName(shopName);
		}
		//只查询审核通过的店铺
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
	

}
