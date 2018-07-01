package xyz.marsj.o2o.enums;

public enum WechatAuthStateEnum {
	LOGINFAIL(-1,"openId输入有误"),SUCCESS(0,"操作成功"),NULL_AUTH_INFO(-1001,"用户信息错误");
	private int state;
	private String stateInfo;
	private WechatAuthStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public static WechatAuthStateEnum stateOf(int state){
		
		for(WechatAuthStateEnum stateEnum:values()){
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
