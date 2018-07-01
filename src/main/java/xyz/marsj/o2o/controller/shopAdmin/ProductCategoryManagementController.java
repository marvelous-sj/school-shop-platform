package xyz.marsj.o2o.controller.shopAdmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.marsj.o2o.dto.ProductCategoryExecution;
import xyz.marsj.o2o.dto.Result;
import xyz.marsj.o2o.entity.ProductCategory;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.enums.ProductCategoryStateEnum;
import xyz.marsj.o2o.exceptions.ProductCategoryOperationException;
import xyz.marsj.o2o.service.IProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	@Autowired
	private IProductCategoryService productCategoryService;

	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> productCategoryList = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			productCategoryList = productCategoryService.queryProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(productCategoryList, true);
		} else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
		}

	}
	
	
	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProductCategorys(HttpServletRequest request,@RequestBody List<ProductCategory> productCategoryList){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for (ProductCategory productCategory : productCategoryList) {
			productCategory.setShopId(currentShop.getShopId());
		}
		if(productCategoryList!=null&&productCategoryList.size()>0){
			try {
				ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
					modelMap.put("success",true);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "至少输入一个商品类别");
		}
		return modelMap;
		
	}
	
	
	@RequestMapping(value = "/deleteproductcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> deleteProductCategory(HttpServletRequest request,Long productCategoryId){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		if(productCategoryId !=null && productCategoryId>0){
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pe = productCategoryService.delectProductCategory(productCategoryId, currentShop.getShopId());
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
					modelMap.put("success",true);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "至少选择一个商品类别");
		}
		return modelMap;
		
	}
}
