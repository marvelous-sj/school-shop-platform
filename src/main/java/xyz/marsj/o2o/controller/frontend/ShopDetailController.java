package xyz.marsj.o2o.controller.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xyz.marsj.o2o.dto.ProductExecution;
import xyz.marsj.o2o.entity.Product;
import xyz.marsj.o2o.entity.ProductCategory;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.service.IProductCategoryService;
import xyz.marsj.o2o.service.IProductService;
import xyz.marsj.o2o.service.IShopService;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
@RestController
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private IProductCategoryService productCategoryService;
	@Autowired
	private IShopService shopService;
	@Autowired
	private IProductService productService;
	@RequestMapping(value="/listshopdetailpageinfo",method=RequestMethod.GET)
	private Map<String,Object> listShopDetailPageInfo(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		long shopId=HttpServletRequestUtil.getLong(request,"shopId");
		if(shopId!=-1L){
			try {
				Shop shop = shopService.queryByShopId(shopId);
				//查询商品目录
				List<ProductCategory> productCategoryList=productCategoryService.queryProductCategoryList(shopId);
				modelMap.put("success", true);
				modelMap.put("shop", shop);
				modelMap.put("productCategoryList", productCategoryList);
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
	
	@RequestMapping(value="/listproductsbyshop",method=RequestMethod.GET)
	private Map<String,Object> listProductByshop(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
		long shopId=HttpServletRequestUtil.getLong(request,"shopId");
		if(pageIndex>-1&&pageSize>-1&&shopId>-1){
			//商品目录
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
		
			//根据输入商品名模糊查询
			String productName=HttpServletRequestUtil.getString(request, "productName");
			
			Product productCondition=compactProductCondition(shopId,productCategoryId,productName);
			ProductExecution pe = productService.queryProductList(productCondition, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("count", pe.getCount());
			modelMap.put("productList",pe.getProductList());
			
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize or shopId is empty");
		}
		return modelMap;
	}


	private Product compactProductCondition(long shopId, long productCategoryId,  String productName) {
		Product productCondition=new Product();
		Shop shop=new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if(productCategoryId!=-1){
			ProductCategory productCategory=new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if(productName!=null){
			productCondition.setProductName(productName);
		}
		//只查询审核通过的商品
		productCondition.setEnableStatus(1);
		return productCondition;
	}
	

}
