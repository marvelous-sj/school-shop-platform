package xyz.marsj.o2o.controller.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xyz.marsj.o2o.entity.HeadLine;
import xyz.marsj.o2o.entity.ShopCategory;
import xyz.marsj.o2o.service.IHeadLineService;
import xyz.marsj.o2o.service.IShopCategoryService;
@RestController
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
	private IHeadLineService headLineService;
	@Autowired
	private IShopCategoryService shopCategoryService;
	
	@RequestMapping(value="/listmainpageinfo",method=RequestMethod.GET)
	private Map<String,Object> listMainPageInfo(){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
		try {
			shopCategoryList = shopCategoryService.queryShopCategoryList(null);
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		List<HeadLine> headLineList=new ArrayList<HeadLine>();
		
		try {
			HeadLine  headLineCondition=new HeadLine();
			headLineCondition.setEnableStatus(1);
			headLineList=headLineService.getHeadLineList(headLineCondition);
			modelMap.put("headLineList", headLineList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		
		
		return modelMap;
		
	}

}
