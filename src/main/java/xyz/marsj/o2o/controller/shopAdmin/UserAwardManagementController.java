package xyz.marsj.o2o.controller.shopAdmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.marsj.o2o.dto.ShopAuthMapExecution;
import xyz.marsj.o2o.dto.UserAccessToken;
import xyz.marsj.o2o.dto.UserAwardMapExecution;
import xyz.marsj.o2o.dto.WechatInfo;
import xyz.marsj.o2o.entity.Award;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.ShopAuthMap;
import xyz.marsj.o2o.entity.UserAwardMap;
import xyz.marsj.o2o.entity.WechatAuth;
import xyz.marsj.o2o.enums.UserAwardMapStateEnum;
import xyz.marsj.o2o.service.IPersonInfoService;
import xyz.marsj.o2o.service.IShopAuthMapService;
import xyz.marsj.o2o.service.IUserAwardMapService;
import xyz.marsj.o2o.service.IWechatAuthService;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
import xyz.marsj.o2o.util.wechat.WechatUtil;

@RestController
@RequestMapping("/shopadmin")
public class UserAwardManagementController {
	@Autowired
	private IUserAwardMapService userAwardMapService;
	@Autowired
	private IWechatAuthService wechatAuthService;
	@Autowired
	private IPersonInfoService personInfoService;
	@Autowired
	private IShopAuthMapService shopAuthMapService;
	@RequestMapping(value="/getuserawardmaplist",method=RequestMethod.GET)
	private Map<String,Object> getUserAwardMapList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if(pageIndex>-1&&pageSize>-1&&currentShop!=null&&currentShop.getShopId()!=null) {
			UserAwardMap userAwardMap=new UserAwardMap();
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			userAwardMap.setShop(currentShop);;
			if(awardName!=null) {
				Award award=new Award();
				award.setAwardName(awardName);
				userAwardMap.setAward(award);
			}
			UserAwardMapExecution uae = userAwardMapService.getUserAwardMapList(userAwardMap, pageIndex, pageSize);
			modelMap.put("count", uae.getCount());
			modelMap.put("userAwardMapList", uae.getUserAwardMapList());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or award");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/exchangeaward",method=RequestMethod.GET)
	private String exchangeAward(HttpServletRequest request,HttpServletResponse response) throws IOException{
		WechatAuth auth= getOperatorInfo(request);
		if(auth!=null) {
			PersonInfo operator = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
			request.getSession().setAttribute("user", operator);
			//获取state后面的url信息
			String QRCodeInfo = URLDecoder.decode(HttpServletRequestUtil.getString(request, "state"), "UTF-8");
			ObjectMapper mapper=new ObjectMapper();
			WechatInfo wechatInfo=null;
			try {
				//解码
				wechatInfo=mapper.readValue(QRCodeInfo.replace("aaa","\""),WechatInfo.class);
			} catch (Exception e) {
				return "shop/operationfail";
			}
			if(!checkQRCodeInfo(wechatInfo)) {
				return "shop/operationfail";
			}
			Long customerId = wechatInfo.getCustomerId();
			Long userAwardId = wechatInfo.getUserAwardId();
			UserAwardMap userAwardMap = compactUserAwardMap4Exchange(customerId,userAwardId,operator);
			if(userAwardMap!=null) {
				//检查是为操作员授权
				try {
					if (!checkShopAuth(operator.getUserId(), userAwardMap)) {
						return "shop/operationfail";
					}
					UserAwardMapExecution se = userAwardMapService.modifyUserAwardMap(userAwardMap);
					if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
						return "shop/operationsuccess";
					} else {
						return "shop/operationfail";
					}
				} catch (RuntimeException e) {
					return "shop/operationfail";
				
			}
		}
	}
		return "shop/operationfail";
	}		
	private boolean checkShopAuth(long userId, UserAwardMap userAwardMap) {
			ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService.listShopAuthMapListById(userAwardMap.getShopId(), 1, 1000);
			for (ShopAuthMap shopAuthMap : shopAuthMapExecution
					.getShopAuthMapList()) {
				if (shopAuthMap.getEmployeeId() == userId) {
					return true;
				}
			}
			return false;
		}
	private UserAwardMap compactUserAwardMap4Exchange(Long customerId,
			Long userAwardId,PersonInfo operator) {
		UserAwardMap userAwardMap = null;
		if (customerId != null && userAwardId != null&&operator.getUserId()!=null) {
			userAwardMap = userAwardMapService.getUserAwardMapById(userAwardId);
			userAwardMap.setUsedStatus(0);
			userAwardMap.setUserId(customerId);
			userAwardMap.setOperator(operator);
		}
		return userAwardMap;
	}
	//检验验证码是否过期（大于5分钟）
	private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
		if(wechatInfo!=null&&wechatInfo.getShopId()!=null&&wechatInfo.getCreateTime()!=null) {
			long currentTime = System.currentTimeMillis();
			if(currentTime-wechatInfo.getCreateTime()<=300000) {
				return true;
			}else {
				return false;
			}
		}else {
		return false;
		}
	}
	//从微信获取用户信息
	private WechatAuth getOperatorInfo(HttpServletRequest request) {
		String code = request.getParameter("code");
		WechatAuth auth=null;
		if(code!=null) {
			try {
				UserAccessToken accessToken = WechatUtil.getUserAccessToken(code);
				String openId = accessToken.getOpenId();
				request.getSession().setAttribute("openId", openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return auth;
	}
}
