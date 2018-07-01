package xyz.marsj.o2o.enums;

public enum ShopAuthMapStateEnum {
	SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"用户信息错误"),NULL_SHOPAUTH_INFO(-1002,"传入了空信息"),
			NULL_SHOPAUTH_ID(-1003,"传入了空Id"),NULL_SHOP_ID(-1004,"传入了空ShopId");
	private int state;
	private String stateInfo;
	private ShopAuthMapStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public static ShopAuthMapStateEnum stateOf(int state){
		
		for(ShopAuthMapStateEnum stateEnum:values()){
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
