package xyz.marsj.o2o.enums;

public enum LocalAuthStateEnum {
	ONLY_ONE_ACCOUNT(-1,"只能绑定一个本地账号"),SUCCESS(0,"操作成功"),NULL_AUTH_INFO(-1001,"用户信息错误");
	private int state;
	private String stateInfo;
	private LocalAuthStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public static LocalAuthStateEnum stateOf(int state){
		
		for(LocalAuthStateEnum stateEnum:values()){
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
