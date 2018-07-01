package xyz.marsj.o2o.controller.shopAdmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.marsj.o2o.dto.EchartSeries;
import xyz.marsj.o2o.dto.EchartXAxis;
import xyz.marsj.o2o.dto.ShopAuthMapExecution;
import xyz.marsj.o2o.dto.UserAccessToken;
import xyz.marsj.o2o.dto.UserProductMapExecution;
import xyz.marsj.o2o.dto.WechatInfo;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Product;
import xyz.marsj.o2o.entity.ProductSellDaily;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.ShopAuthMap;
import xyz.marsj.o2o.entity.UserProductMap;
import xyz.marsj.o2o.entity.WechatAuth;
import xyz.marsj.o2o.enums.UserProductMapStateEnum;
import xyz.marsj.o2o.exceptions.UserProductMapOperationException;
import xyz.marsj.o2o.service.IProductSellDailyService;
import xyz.marsj.o2o.service.IProductService;
import xyz.marsj.o2o.service.IShopAuthMapService;
import xyz.marsj.o2o.service.IUserProductMapService;
import xyz.marsj.o2o.service.IWechatAuthService;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
import xyz.marsj.o2o.util.wechat.WechatUtil;

@RestController
@RequestMapping("/shopadmin")
public class UserProductManagementController {
	@Autowired
	private IUserProductMapService userProductMapService;
	@Autowired
	private IProductSellDailyService productSellDailyService;
	@Autowired
	private IWechatAuthService wechatAuthService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IShopAuthMapService shopAuthMapService;
	@RequestMapping(value="/listuserproductmapsbyshop",method=RequestMethod.GET)
	private Map<String,Object> listUserProductMapsByShop(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if(pageIndex>-1&&pageSize>-1&&currentShop!=null&&currentShop.getShopId()!=null) {
			UserProductMap userProductMapCondition=new UserProductMap();
			userProductMapCondition.setShop(currentShop);
			String productName=HttpServletRequestUtil.getString(request, "productName");
			if(productName!=null) {
				Product product=new Product();
				product.setProductName(productName);
				userProductMapCondition.setProduct(product);
				userProductMapCondition.setProductName(productName);
			}
			UserProductMapExecution upme = userProductMapService.listUserProductMap(userProductMapCondition, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("userProductMapList", upme.getUserProductMapList());
			modelMap.put("count", upme.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId or pageIndex or pageSize");
		}
		return modelMap;
		
	}
	
	@RequestMapping(value="/listproductselldailyinfobyshop",method=RequestMethod.GET)
	private Map<String,Object> listProductSellDailyInfoByShop(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if(currentShop!=null&&currentShop.getShopId()!=null) {
			ProductSellDaily productSellDailyCondition=new ProductSellDaily();
			productSellDailyCondition.setShop(currentShop);
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date endTime = cal.getTime();
			cal.add(Calendar.DATE, -6);
			Date beginTime = cal.getTime();
			List<ProductSellDaily> productSellDailyList = productSellDailyService.queryProductSellDaily(productSellDailyCondition, beginTime, endTime);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//商品名称列表
			HashSet<String> legendData = new LinkedHashSet<String>();
			//x轴数据
			HashSet<String> xData = new LinkedHashSet<String>();
			List<EchartSeries> series=new ArrayList<EchartSeries>();
			//日销量列表
			List<Integer> totalList=new ArrayList<Integer>();
			//当期商品名称，默认为空
			String currentProductName="";
			for (int i = 0; i < productSellDailyList.size(); i++) {
				ProductSellDaily psd=productSellDailyList.get(i);
				legendData.add(psd.getProduct().getProductName());
				xData.add(simpleDateFormat.format(psd.getCreateTime()));
				//判断是否为同一商品
				if(!currentProductName.equals(psd.getProduct().getProductName())&&!currentProductName.isEmpty()) {
					//不是同一商品
					EchartSeries es=new EchartSeries();
					es.setName(currentProductName);
					es.setData(totalList.subList(0, totalList.size()));
					series.add(es);
					//置空
					totalList=new ArrayList<Integer>();
					currentProductName=psd.getProduct().getProductName();
					totalList.add(psd.getTotal());
				}else {
					totalList.add(psd.getTotal());
					currentProductName=psd.getProduct().getProductName();
				}
				if(i==productSellDailyList.size()-1) {
					EchartSeries es=new EchartSeries();
					es.setName(currentProductName);
					es.setData(totalList.subList(0, totalList.size()));
					series.add(es);
				}
			}
			modelMap.put("legendData",legendData);
			modelMap.put("series",series);
			List<EchartXAxis> xAxis=new ArrayList<>();
			EchartXAxis echartXAxis = new EchartXAxis();
			echartXAxis.setData(xData);
			xAxis.add(echartXAxis);
			modelMap.put("success", true);
		
			modelMap.put("xAxis",xAxis);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shop or shopId");
		}
		return modelMap;
	}
	@RequestMapping(value="/adduserproductmap",method=RequestMethod.GET)
	private String addUserProductMap(HttpServletRequest request,HttpServletResponse response) throws IOException{
		WechatAuth auth= getOperatorInfo(request);
		if(auth!=null) {
			PersonInfo operator = auth.getPersonInfo();
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
			Long productId = wechatInfo.getProductId();
			UserProductMap userProductMap=null;
			if(customerId!=null&&productId!=null) {
				PersonInfo cust=new PersonInfo();
				userProductMap=new UserProductMap();
				cust.setUserId(customerId);
				Product product = productService.queryProductById(productId);
				userProductMap.setProduct(product);
				userProductMap.setUser(cust);
				userProductMap.setCreateTime(new Date());
				userProductMap.setShop(product.getShop());
				userProductMap.setPoint(product.getPoint());
			}
			if(userProductMap!=null&&customerId!=-1) {
				try {
					if(!checkShopAuth(operator.getUserId(),userProductMap)) {
						return "shop/operationfail";
					}
					UserProductMapExecution upe = userProductMapService.addUserProductMap(userProductMap);
					if(upe.getState()==UserProductMapStateEnum.SUCCESS.getState()) {
						return "shop/operationsuccess";
					}
				} catch (UserProductMapOperationException e) {
					return "shop/operationfail";
				}
			}
			}
		return "shop/operationfail";
	}
	private boolean checkShopAuth(Long userId, UserProductMap userProductMap) {
		ShopAuthMapExecution sae = shopAuthMapService.listShopAuthMapListById(userProductMap.getShop().getShopId(), 1, 1000);
		for (ShopAuthMap shopAuthMap : sae.getShopAuthMapList()) {
			if(shopAuthMap.getEmployee().getUserId()==userId) {
				return true;
			}
		}
		return false;
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
	
