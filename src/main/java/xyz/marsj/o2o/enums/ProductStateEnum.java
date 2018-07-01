package xyz.marsj.o2o.enums;

public enum ProductStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"商品非法"),SUCCESS(1,"操作成功"),PASS(2,"审核通过"),INNER_ERROR(-1001,"内部系统错误"),NULL_PRODUCTID(-1002,"ProductId为空"),NULL_PRODUCT(-1003,"Product为空");
	private int state;
	private String stateInfo;
	private ProductStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public static ProductStateEnum stateOf(int state){
		
		for(ProductStateEnum stateEnum:values()){
			if(stateEnum.state==state){
				return stateEnum;
			}
		}
		return null;
	}
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	
	

}
