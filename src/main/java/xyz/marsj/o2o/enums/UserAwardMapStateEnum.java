package xyz.marsj.o2o.enums;

public enum UserAwardMapStateEnum {
	ERROR(-1001,"内部错误"),FAIL(-1,"操作失败"),SUCCESS(1,"操作成功");
	private int state;
	private String stateInfo;
	private UserAwardMapStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public static UserAwardMapStateEnum stateOf(int state){
		
		for(UserAwardMapStateEnum stateEnum:values()){
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
