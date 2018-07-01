package xyz.marsj.o2o.controller.frontend;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import xyz.marsj.o2o.dto.UserAwardMapExecution;
import xyz.marsj.o2o.dto.UserProductMapExecution;
import xyz.marsj.o2o.entity.Award;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Product;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.UserAwardMap;
import xyz.marsj.o2o.entity.UserProductMap;
import xyz.marsj.o2o.enums.UserAwardMapStateEnum;
import xyz.marsj.o2o.service.IAwardService;
import xyz.marsj.o2o.service.IPersonInfoService;
import xyz.marsj.o2o.service.IUserAwardMapService;
import xyz.marsj.o2o.service.IUserProductMapService;
import xyz.marsj.o2o.util.CodeUtil;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
import xyz.marsj.o2o.util.ShortNetAddress;
@RestController
@RequestMapping("/frontend")
public class MyAwardController {
	@Autowired 
	private IAwardService awardService;
	@Autowired
	private IUserAwardMapService userAwardMapService;
	@Autowired
	private IUserProductMapService userProductMapService;
	@Autowired
	private IPersonInfoService personInfoService;
	
	@RequestMapping(value="/getawardbyuserawardid",method=RequestMethod.GET)
	private Map<String, Object> getAwardByUserAwardId(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		long userAwardId = HttpServletRequestUtil.getLong(request, "userAwardId");
		if(userAwardId>-1) {
			//获取奖品映射
			UserAwardMap userAwardMap = userAwardMapService.getUserAwardMapByIdFrontend(userAwardId);
			//获取奖品信息
			Award award = awardService.getAwardByAwardId(userAwardMap.getAward().getAwardId());
			modelMap.put("success", true);
			modelMap.put("award", award);
			modelMap.put("usedStatus", userAwardMap.getUsedStatus());
			modelMap.put("userAwardMap",userAwardMap);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty awardId");
		}
		return modelMap;
	}
	
	
	@RequestMapping(value="/adduserawardmap",method=RequestMethod.GET)
	private Map<String, Object> addUserAwardMap(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		if(awardId!=null&&personInfo.getUserId()!=null) {
			Award award = awardService.getAwardByAwardId(awardId);
			UserAwardMap userAwardMap=new UserAwardMap();
			Shop shop=new Shop();
			shop.setShopId(award.getShopId());
			userAwardMap.setAward(award);
			userAwardMap.setShop(shop);
			userAwardMap.setUser(personInfo);
			UserAwardMapExecution uae = userAwardMapService.addUserAwardMap(userAwardMap);
			if(uae.getState()==UserAwardMapStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);				
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", uae.getStateInfo());
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "传入参数有误");
		}
		return modelMap;
		
	}
	
	@RequestMapping(value="/listuserawardmapsbycustomer",method=RequestMethod.GET)
	private Map<String, Object> listUserAwardMapsByCustomer(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		if(pageIndex>-1&&pageSize>-1&&personInfo!=null&&personInfo.getUserId()!=null) {
			UserAwardMap userAwardMap=new UserAwardMap();
			userAwardMap.setUser(personInfo);
			long shopId = HttpServletRequestUtil.getLong(request, "shopId");
			if(shopId>-1) {
				Shop shop=new Shop();
				shop.setShopId(shopId);
				userAwardMap.setShop(shop);;
			}
			String awardName=HttpServletRequestUtil.getString(request, "awardName");
			if(awardName!=null) {
				Award award=new Award();
				award.setAwardName(awardName);
				userAwardMap.setAward(award);
			}
			
			UserAwardMapExecution uae = userAwardMapService.listUserAwardMap(userAwardMap, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("userAwardMapList", uae.getUserAwardMapList());
			modelMap.put("count", uae.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or user");
		}
		return modelMap;
	}
	private static String urlPrefix;
	private static String urlMiddle;
	private static String urlSuffix;
	private static String exchangeUrl;
	@Value("${wechat.prefix}")
	public  void setUrlPrefix(String urlPrefix) {
		MyAwardController.urlPrefix = urlPrefix;
	}
	@Value("${wechat.middle}")
	public  void setUrlMiddle(String urlMiddle) {
		MyAwardController.urlMiddle = urlMiddle;
	}
	@Value("${wechat.suffix}")
	public  void setUrlSuffix(String urlSuffix) {
		MyAwardController.urlSuffix = urlSuffix;
	}
	@Value("${wechat.exchange.url}")
	public  void setAuthUrl(String exchangeUrl) {
		MyAwardController.exchangeUrl = exchangeUrl;
	}
	
	//生成奖品兑换二维码
	@RequestMapping(value="/generateqrcode4saward",method=RequestMethod.GET)
	public void generateQRCode4Award(HttpServletRequest request,HttpServletResponse response){
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		long userAwardId = HttpServletRequestUtil.getLong(request, "userAwardId");
		UserAwardMap userAwardMap = userAwardMapService.getUserAwardMapById(userAwardId);
		if(userAwardMap!=null&&user!=null&user.getUserId()!=null&&userAwardMap.getUser().getUserId()==user.getUserId()) {
			long timeStamp=System.currentTimeMillis();
			String content="{aaauserAwardIdaaa:"+userAwardId+",aaacustomerIdaaa:"+user.getUserId()+",aaacreateTimeaaa:"+timeStamp+"}";
			String longUrl;
			try {
				longUrl = urlPrefix+exchangeUrl+urlMiddle+URLEncoder.encode(content, "UTF-8")+urlSuffix;
				String shortURL = ShortNetAddress.getShortURL(longUrl);
				BitMatrix bitMatrix = CodeUtil.generateQRCodeStream(shortURL, response);
				MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	@RequestMapping(value="/listuserproductmapsbycustomer",method=RequestMethod.GET)
	private Map<String, Object> listUserProductMapsByCustomer(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		if(pageIndex>-1&&pageSize>-1&&personInfo!=null&&personInfo.getUserId()!=null) {
			UserProductMap userProductMap=new UserProductMap();
			userProductMap.setUser(personInfo);
			long shopId = HttpServletRequestUtil.getLong(request, "shopId");
			if(shopId>-1) {
				Shop shop=new Shop();
				shop.setShopId(shopId);
				userProductMap.setShop(shop);;
			}
			//根据商品名称模糊查询
			String productName=HttpServletRequestUtil.getString(request, "productName");
			if(productName!=null) {
				Product product=new Product();
				product.setProductName(productName);
				userProductMap.setProduct(product);
			}
			
			UserProductMapExecution use = userProductMapService.listUserProductMap(userProductMap, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("userProductMapList", use.getUserProductMapList());
			modelMap.put("count", use.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or user");
		}
		return modelMap;
	}
}