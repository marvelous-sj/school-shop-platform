package xyz.marsj.o2o.enums;

public enum ProductCategoryStateEnum {
	OFFLINE(-1,"商铺非法"),SUCCESS(1,"操作成功"),EMPTY_LIST(-1002,"添加数少于1"),INNER_ERROR(-1001,"操作失败");
	private int state;
	private String stateInfo;
	private ProductCategoryStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public static ProductCategoryStateEnum stateOf(int state){
		
		for(ProductCategoryStateEnum stateEnum:values()){
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
