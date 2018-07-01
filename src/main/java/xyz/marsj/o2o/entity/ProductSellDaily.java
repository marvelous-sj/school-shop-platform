package xyz.marsj.o2o.entity;

import java.util.Date;

public class ProductSellDaily {
	private Long productSellDailyId;
	//销量
	private Integer total;
	private Date createTime;
	private Product product;
	private Shop shop;
	
	public Long getProductSellDailyId() {
		return productSellDailyId;
	}
	public void setProductSellDailyId(Long productSellDailyId) {
		this.productSellDailyId = productSellDailyId;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	

}
