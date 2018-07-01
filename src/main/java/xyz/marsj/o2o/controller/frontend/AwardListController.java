package xyz.marsj.o2o.controller.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xyz.marsj.o2o.dto.AwardExecution;
import xyz.marsj.o2o.entity.Award;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.UserShopMap;
import xyz.marsj.o2o.service.IAwardService;
import xyz.marsj.o2o.service.IUserShopMapService;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
@RestController
@RequestMapping("/frontend")
public class AwardListController {
	@Autowired 
	private IAwardService awardService;
	@Autowired
	private IUserShopMapService userShopMapService;
	@RequestMapping(value="/listawardspageinfo",method=RequestMethod.GET)
	private Map<String, Object> listAwardsByShop(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		if(personInfo!=null&&personInfo.getUserId()!=null) {
			UserShopMap userShopMap = userShopMapService.getUserShopMap(personInfo.getUserId(), shopId);
			modelMap.put("point", userShopMap.getPoint());
		}
		if(pageIndex>-1&&pageSize>-1&&shopId>-1){
			Award award=new Award();
			award.setShopId(shopId);
			String awardName=HttpServletRequestUtil.getString(request, "awardName");
			if(awardName!=null) {
				award.setAwardName(awardName);
			}
			AwardExecution ae = awardService.getAwardList(award, pageIndex, pageSize);
			modelMap.put("awardList", ae.getAwardList());
			modelMap.put("count", ae.getCount());
			modelMap.put("success", true);
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "传入参数有误");
		}
		return modelMap;
		
	}
	
	
}