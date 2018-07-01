package xyz.marsj.o2o.dto;

import java.util.List;

import xyz.marsj.o2o.entity.LocalAuth;
import xyz.marsj.o2o.enums.LocalAuthStateEnum;

public class LocalAuthExecution {
	private int state;
	private String stateInfo;
	private int count;
	private LocalAuth localAuth;
	private List<LocalAuth> localAuthList;
	public LocalAuthExecution() {
		
	}
	//操作本地账户失败时使用的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//操作本地账户成功时使用的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum,LocalAuth localAuth) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.localAuth=localAuth;
	}	
	//操作本地账户成功时使用的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum,List<LocalAuth> localAuthList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.localAuthList=localAuthList;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public LocalAuth getLocalAuth() {
		return localAuth;
	}
	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}
	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}
	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}



	
}
