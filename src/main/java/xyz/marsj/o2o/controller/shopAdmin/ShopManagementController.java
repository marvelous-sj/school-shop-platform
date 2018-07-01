package xyz.marsj.o2o.controller.shopAdmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.dto.ShopExecution;
import xyz.marsj.o2o.entity.Area;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.entity.ShopCategory;
import xyz.marsj.o2o.enums.ShopStateEnum;
import xyz.marsj.o2o.service.IAreaService;
import xyz.marsj.o2o.service.IShopCategoryService;
import xyz.marsj.o2o.service.IShopService;
import xyz.marsj.o2o.util.CodeUtil;
import xyz.marsj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private IShopService shopService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private IShopCategoryService shopCategoryService;
	@RequestMapping(value="/getshopmanagementinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId<=0){
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if(currentShopObj==null){
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			}else{
				Shop currentShop=(Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		}else{
			Shop currentShop=new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
		
	}
	
	
	
	
	//获取当前User创建的所有店铺信息
	@RequestMapping(value="/getshoplist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition=new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.queryShopList(shopCondition, 0,50);
			modelMap.put("shopList",se.getShopList());
			modelMap.put("user",user);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	
	
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> 	getShopById(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId>-1){
			try {
				Shop shop = shopService.queryByShopId(shopId);
				List<Area> areaList = new ArrayList<Area>();
				areaList= areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				 }
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
		
	}
	
	@RequestMapping(value="/getshopinitinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopInitInfo(){
		ShopCategory shopCategory=new ShopCategory();
		Map<String,Object> modelMap=new HashMap<String,Object>();
		List<Area> areaList = new ArrayList<Area>();
		List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
		try{
			areaList =areaService.getAreaList();
			shopCategoryList=shopCategoryService.queryShopCategoryList(shopCategory);
			modelMap.put("areaList", areaList);
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("success", true);
		}catch(Exception e){
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		// 接受参数，店铺信息，图片
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 注册店铺
		if (shop != null && shopImg != null) {
			PersonInfo personInfo = new PersonInfo();
			personInfo= (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(personInfo);
			ShopExecution se;
			try {
				ImgHolder imgHolder = new ImgHolder( shopImg.getInputStream(), shopImg.getOriginalFilename());
				se = shopService.addShop(shop,imgHolder);
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					@SuppressWarnings("unchecked")
					List<Shop> shopList=(List<Shop>) request.getSession().getAttribute("shopList");
					if(shopList==null||shopList.size()==0){
						shopList=new ArrayList<>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList",shopList);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap; 
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "店铺信息不能为空");
			return modelMap;
		}

	}
	// private static void inputStreamToFile(InputStream in,File file){
	// OutputStream out=null;
	// try {
	// out=new FileOutputStream(file);
	// int bytesRead=0;
	// byte[] bytes=new byte[1024];
	// while((bytesRead=in.read(bytes))!=-1){
	// out.write(bytes, 0, bytesRead);
	// }
	// } catch (Exception e) {
	// throw new RuntimeException("调用inputStreamToFile失败"+e.getMessage());
	// }finally {
	// try {
	//
	// if(in!=null){
	// in.close();
	// }
	// if(out!=null){
	// out.close();
	// }
	// } catch (IOException e) {
	// throw new RuntimeException("关闭inputStreamToFile的IO流失败"+e.getMessage());
	// }
	//
	// }
	// }
	
	
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		// 接受参数，店铺信息，图片
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} 
		// 修改店铺信息
		if (shop != null && shop.getShopId()!=null) {
			ShopExecution se;
			ImgHolder imgHolder;
			try {
				if(shopImg==null){
					se=shopService.modifyShop(shop, null); 
				}else{
					imgHolder = new ImgHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
					se = shopService.modifyShop(shop,imgHolder);					
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap; 
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "店铺Id不能为空");
			return modelMap;
		}
}
}
