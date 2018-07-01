package xyz.marsj.o2o.dto;

import java.util.List;

import xyz.marsj.o2o.entity.ProductCategory;
import xyz.marsj.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
	private int state;
	private String stateInfo;
	private List<ProductCategory> productCategory;
	public ProductCategoryExecution() {
		
	}
	//操作店铺失败时使用的构造器
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//操作店铺成功时使用的构造器
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategory) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.productCategory = productCategory;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public List<ProductCategory> getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(List<ProductCategory> productCategory) {
		this.productCategory = productCategory;
	}

	
	
	
	
}
