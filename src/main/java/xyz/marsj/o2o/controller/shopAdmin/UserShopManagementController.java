package xyz.marsj.o2o.controller.shopAdmin;

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
@RequestMapping("/shopadmin")
public class UserShopManagementController {
	@Autowired
	private IUserShopMapService userShopMapService;
	@RequestMapping(value="/getusershopmaplist",method=RequestMethod.GET)
	private Map<String,Object> getUserShopMapList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if(pageIndex>-1&&pageSize>-1&&currentShop!=null&&currentShop.getShopId()!=null) {
			UserShopMap userShopMap=new UserShopMap();
			String userName = HttpServletRequestUtil.getString(request, "userName");
			userShopMap.setShop(currentShop);
			if(userName!=null) {
				PersonInfo user=new PersonInfo();
				user.setName(userName);
				userShopMap.setUser(user);
			}
			UserShopMapExecution use = userShopMapService.getUserShopMapList(userShopMap, pageIndex, pageSize);
			modelMap.put("count", use.getCount());
			modelMap.put("userShopMapList", use.getUserShopMapList());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or shop");
		}
		return modelMap;
	}
}
