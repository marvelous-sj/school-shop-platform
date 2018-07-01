package xyz.marsj.o2o.controller.shopAdmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import xyz.marsj.o2o.dto.ShopAuthMapExecution;
import xyz.marsj.o2o.dto.UserAccessToken;
import xyz.marsj.o2o.dto.WechatInfo;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.ShopAuthMap;
import xyz.marsj.o2o.entity.WechatAuth;
import xyz.marsj.o2o.enums.ShopAuthMapStateEnum;
import xyz.marsj.o2o.exceptions.ShopAuthMapOperationException;
import xyz.marsj.o2o.service.IPersonInfoService;
import xyz.marsj.o2o.service.IShopAuthMapService;
import xyz.marsj.o2o.service.IWechatAuthService;
import xyz.marsj.o2o.util.CodeUtil;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
import xyz.marsj.o2o.util.ShortNetAddress;
import xyz.marsj.o2o.util.wechat.WechatUtil;

@RestController
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {
	@Autowired
	private IShopAuthMapService shopAuthMapService;
	@Autowired
	private IWechatAuthService wechatAuthService;
	@Autowired
	private IPersonInfoService personInfoService;
	@RequestMapping(value="/listshopauthmapbyshopid",method=RequestMethod.GET)
		public Map<String,Object> listShopAuthMapByShopId(HttpServletRequest request){
		Map<String,Object> modelMap =new HashMap<String,Object>();
		Shop currentShop=(Shop)request.getSession().getAttribute("currentShop");
		if(currentShop!=null&&currentShop.getShopId()!=null) {
			ShopAuthMapExecution se = shopAuthMapService.listShopAuthMapListById(currentShop.getShopId(), 1, 100);
			
				modelMap.put("success", true);
				modelMap.put("shopAuthMapList", se.getShopAuthMapList());
				modelMap.put("count", se.getCount());			
			}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty Shop or ShopId");
		}
		return modelMap;	
		}
	
	@RequestMapping(value="/getshopauthmapbyid",method=RequestMethod.GET)
	public Map<String,Object> getShopAuthMapById(@RequestParam Long shopAuthId){
		Map<String,Object> modelMap =new HashMap<String,Object>();
		if(shopAuthId!=null&&shopAuthId>-1) {
			ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
			modelMap.put("success", true);
			modelMap.put("shopAuthMap", shopAuthMap);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty ShopAuthId");
		}
		return modelMap;
		
	}
	
	@RequestMapping(value="/modifyshopauthmap",method=RequestMethod.POST)
	public Map<String,Object> modifyShopAuthMap(String shopAuthMapStr,HttpServletRequest request){
		Map<String,Object> modelMap =new HashMap<String,Object>();
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		//修改需要验证码，而删除/恢复不需要
		if(!statusChange&&!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg","验证码输入有误!");
			return modelMap;
		}
		ObjectMapper objectMapper=new ObjectMapper();
		ShopAuthMap shopAuthMap =null;
		try {
			 shopAuthMap = objectMapper.readValue(shopAuthMapStr, ShopAuthMap.class);
		} catch (IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		if(shopAuthMap!=null&&shopAuthMap.getShopAuthId()!=null) {
			try {
				//检查是否为店家本身，若是，则不能修改自己权限
				if(!checkPermission(shopAuthMap.getShopAuthId())) {
					modelMap.put("success", false);
					modelMap.put("errMsg","无法对店家老板权限进行操作(已经是最高权限)");
					return modelMap;
				}
				ShopAuthMapExecution se = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
				if(se.getState()==ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("shopAuthMap", se.getShopAuthMap());				
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopAuthMapOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty ShopAuthId");
		}
		
		return modelMap;
	}

	private boolean checkPermission(Long shopAuthId) {
		ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
		if(shopAuthMap.getTitleFlag()==0) {
			return false;
		}else {
			return true;
		}
		
	}
	
	private static String urlPrefix;
	private static String urlMiddle;
	private static String urlSuffix;
	private static String authUrl;
	@Value("${wechat.prefix}")
	public  void setUrlPrefix(String urlPrefix) {
		ShopAuthManagementController.urlPrefix = urlPrefix;
	}
	@Value("${wechat.middle}")
	public  void setUrlMiddle(String urlMiddle) {
		ShopAuthManagementController.urlMiddle = urlMiddle;
	}
	@Value("${wechat.suffix}")
	public  void setUrlSuffix(String urlSuffix) {
		ShopAuthManagementController.urlSuffix = urlSuffix;
	}
	@Value("${wechat.auth.url}")
	public  void setAuthUrl(String authUrl) {
		ShopAuthManagementController.authUrl = authUrl;
	}
	
	//生成授权二维码
	@RequestMapping(value="/generateqrcode4shopauth",method=RequestMethod.GET)
	public void generateQRCode4ShopAuth(HttpServletRequest request,HttpServletResponse response){
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		if(shop!=null&&shop.getShopId()!=null) {
			long timeStamp=System.currentTimeMillis();
			String content="{aaashopIdaaa:"+shop.getShopId()+",aaacreateTimeaaa:"+timeStamp+"}";
			String longUrl;
			try {
				longUrl = urlPrefix+authUrl+urlMiddle+URLEncoder.encode(content, "UTF-8")+urlSuffix;
				String shortURL = ShortNetAddress.getShortURL(longUrl);
				BitMatrix bitMatrix = CodeUtil.generateQRCodeStream(shortURL, response);
				MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	@RequestMapping(value="/addshopauthmap",method=RequestMethod.GET)
	public String addShopAuthMap(HttpServletRequest request,HttpServletResponse response) throws IOException{
		WechatAuth auth= getEmployeeInfo(request);
		if(auth!=null) {
			PersonInfo user = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
			request.getSession().setAttribute("user", user);
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
			//检查是否已经添加授权
			ShopAuthMapExecution selist = shopAuthMapService.listShopAuthMapListById(wechatInfo.getShopId(), 1, 100);
			List<ShopAuthMap> shopAuthMapList = selist.getShopAuthMapList();
			for (ShopAuthMap shopAuthMap : shopAuthMapList) {
				if(shopAuthMap.getEmployee().getUserId()==user.getUserId()) {
					return "shop/operationfail";
				}
				
			}
			try {
				//添加授权
				ShopAuthMap shopAuthMap=new ShopAuthMap();
				Shop shop=new Shop();
				shop.setShopId(wechatInfo.getShopId());
				shopAuthMap.setShop(shop);
				shopAuthMap.setTitle("员工");
				shopAuthMap.setTitleFlag(1);
				shopAuthMap.setEmployee(user);
				ShopAuthMapExecution se = shopAuthMapService.addShopAuthMap(shopAuthMap);
				if(se.getState()==ShopAuthMapStateEnum.SUCCESS.getState()) {
					return "shop/operationsuccess";
				}else {
					return "shop/operationfail";
				}
			} catch (ShopAuthMapOperationException e) {
				return "shop/operationfail";
			}
		}
		return "shop/operationfail";
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
	private WechatAuth getEmployeeInfo(HttpServletRequest request) {
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
