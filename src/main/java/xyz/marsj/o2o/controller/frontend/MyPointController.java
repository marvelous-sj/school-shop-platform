package xyz.marsj.o2o.controller.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xyz.marsj.o2o.dto.UserShopMapExecution;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.UserShopMap;
import xyz.marsj.o2o.service.IUserShopMapService;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
@RestController
@RequestMapping("/frontend")
public class MyPointController {
	@Autowired 
	private IUserShopMapService userShopMapService;
	@RequestMapping(value="/listusershopmapsbycustomer",method=RequestMethod.GET)
	private Map<String, Object> listUserShopMapsByCustomer(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		if(pageIndex>-1&&pageSize>-1&&personInfo!=null&&personInfo.getUserId()!=null) {
			UserShopMap userShopMap=new UserShopMap();
			userShopMap.setUser(personInfo);
			long shopId = HttpServletRequestUtil.getLong(request, "shopId");
			if(shopId>-1) {
				Shop shop=new Shop();
				shop.setShopId(shopId);
				userShopMap.setShop(shop);;
			}
			UserShopMapExecution uae = userShopMapService.getUserShopMapList(userShopMap, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("userShopMapList", uae.getUserShopMapList());
			modelMap.put("count", uae.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or user");
		}
		return modelMap;
	}

}