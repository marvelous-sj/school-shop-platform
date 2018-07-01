package xyz.marsj.o2o.controller.shopAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.dto.ProductExecution;
import xyz.marsj.o2o.entity.Product;
import xyz.marsj.o2o.entity.ProductCategory;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.enums.ProductStateEnum;
import xyz.marsj.o2o.exceptions.ProductOperationException;
import xyz.marsj.o2o.service.IProductCategoryService;
import xyz.marsj.o2o.service.IProductService;
import xyz.marsj.o2o.util.CodeUtil;
import xyz.marsj.o2o.util.HttpServletRequestUtil;

@RestController
@RequestMapping("/shopadmin")
public class ProductManagementController {
@Autowired
private IProductService productService;
@Autowired
private IProductCategoryService productCategoryService;
private static final int MAXIMGCOUNT=6;
@RequestMapping(value="/addproduct",method=RequestMethod.POST)
private Map<String, Object> addProduct(HttpServletRequest request){
	Map<String, Object> modelMap=new HashMap<String,Object>();
	if(!CodeUtil.checkVerifyCode(request)){
		modelMap.put("success", false);
		modelMap.put("errMsg", "验证码输入错误");
		return modelMap;
	}
	ObjectMapper mapper=new ObjectMapper();
	Product product=null;
	String productStr = HttpServletRequestUtil.getString(request,"productStr");
	ImgHolder thumbnail=null;
	List<ImgHolder> imgList=new ArrayList<ImgHolder>();
	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	//若存在文件流
	try {
		if(multipartResolver.isMultipart(request)){
		thumbnail = handleImg(request, thumbnail, imgList);
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//把前端传来的productStr转换为Product实体类
		product = mapper.readValue(productStr, Product.class);
	} catch (Exception e) {
		modelMap.put("success", false);
		modelMap.put("errMsg", e.getMessage());
		return modelMap;
		}
	
	if(product!=null&&thumbnail!=null&&imgList.size()>0){
		try {
			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			product.setShop(currentShop);
			
			ProductExecution pe = productService.addProduct(product, thumbnail, imgList);
			if(pe.getState()==ProductStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", pe.getStateInfo());
			}
		} catch (ProductOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		
	}else{
		modelMap.put("success", false);
		modelMap.put("errMsg", "请输入商品信息");
	}
	return modelMap;
	
}

private ImgHolder handleImg(HttpServletRequest request, ImgHolder thumbnail, List<ImgHolder> imgList)
		throws IOException {
	MultipartHttpServletRequest	multipartRequest=(MultipartHttpServletRequest) request;
	CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
	if(file!=null){
		thumbnail=new ImgHolder(file.getInputStream(),file.getOriginalFilename());			
	}
	for(int i=0;i<MAXIMGCOUNT;i++){
	CommonsMultipartFile productFile=(CommonsMultipartFile) multipartRequest.getFile("productImg"+i);
		if(productFile!=null){
			ImgHolder productImg=new ImgHolder(productFile.getInputStream(), productFile.getOriginalFilename());
			imgList.add(productImg);
			}else{
				break;
			}
		}
	return thumbnail;
}


@RequestMapping(value="/modifyproduct",method=RequestMethod.POST)
private Map<String, Object> modifyProduct(HttpServletRequest request){
	Map<String, Object> modelMap=new HashMap<String,Object>();
	boolean statusChange=HttpServletRequestUtil.getBoolean(request, "statusChange");
	if(!statusChange&&!CodeUtil.checkVerifyCode(request)){
		modelMap.put("success", false);
		modelMap.put("errMsg", "验证码输入错误");
		return modelMap;
	}
	ObjectMapper mapper=new ObjectMapper();
	Product product=null;
	String productStr = HttpServletRequestUtil.getString(request,"productStr");
	ImgHolder thumbnail=null;
	List<ImgHolder> imgList=new ArrayList<ImgHolder>();
	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	//若存在文件流
	try {
		if(multipartResolver.isMultipart(request)){
		thumbnail = handleImg(request, thumbnail, imgList);
		}
		//把前端传来的productStr转换为Product实体类
		product = mapper.readValue(productStr, Product.class);
	} catch (Exception e) {
		modelMap.put("success", false);
		modelMap.put("errMsg", e.getMessage());
		return modelMap;
		}
	
	if(product!=null){
		try {
			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			product.setShop(currentShop);
			ProductExecution pe = productService.modifyProduct(product, thumbnail, imgList);
			if(pe.getState()==ProductStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", pe.getStateInfo());
			}
		} catch (ProductOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		
	}else{
		modelMap.put("success", false);
		modelMap.put("errMsg", "请输入商品信息");
	}
	return modelMap;
	
}

@RequestMapping(value="/getproductbyid",method=RequestMethod.GET)
private Map<String, Object> getProductById(@RequestParam Long productId){
	Map<String,Object> modelMap=new HashMap<String,Object>();
	if(productId>=1){
		Product product = productService.queryProductById(productId);
		List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryList(product.getShop().getShopId());
		modelMap.put("success", true);
		modelMap.put("product", product);
		modelMap.put("productCategoryList", productCategoryList);
	}else{
		modelMap.put("success", false);
		modelMap.put("errMsg", "empty productId");
	}
	return modelMap;
}



@RequestMapping(value="/getproductlistbyshop",method=RequestMethod.GET)
private Map<String, Object> getProductListByShop(HttpServletRequest request){
	Map<String,Object> modelMap=new HashMap<String,Object>();
	Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
	int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
	int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
	if(pageIndex>-1&&pageSize>-1&&currentShop!=null&&currentShop.getShopId()!=null){
		
		String productName=HttpServletRequestUtil.getString(request, "productName");
		Long productCategoryId=HttpServletRequestUtil.getLong(request, "productCategoryId");
		
		Product productCondition=new Product();
		productCondition.setShop(currentShop);
		if(productCategoryId>-1){
			ProductCategory productCategory=new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if(productName!=null){
			productCondition.setProductName(productName);
		}
		ProductExecution pe = productService.queryProductList(productCondition, pageIndex, pageSize);
		modelMap.put("productList", pe.getProductList());
		modelMap.put("count", pe.getCount());
		modelMap.put("success", true);
	}else{
		modelMap.put("success", false);
		modelMap.put("errMsg", "传入参数有误");
	}
	return modelMap;
	
}

}
