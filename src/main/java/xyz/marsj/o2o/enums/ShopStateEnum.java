package xyz.marsj.o2o.enums;

public enum ShopStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"店铺非法"),SUCCESS(1,"操作成功"),PASS(2,"审核通过"),INNER_ERROR(-1001,"内部系统错误"),NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"shop为空");
	private int state;
	private String stateInfo;
	private ShopStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public static ShopStateEnum stateOf(int state){
		
		for(ShopStateEnum stateEnum:values()){
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
