package xyz.marsj.o2o.dto;

import java.util.List;

import xyz.marsj.o2o.entity.UserAwardMap;
import xyz.marsj.o2o.enums.UserAwardMapStateEnum;

public class UserAwardMapExecution {
	private int state;
	private String stateInfo;
	private int count;
	private UserAwardMap UserAwardMap;
	private List<UserAwardMap> UserAwardMapList;
	public UserAwardMapExecution() {
		
	}
	//生成商品销量失败时使用的构造器
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//生成商品销量成功时使用的构造器
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum,UserAwardMap userAwardMap) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.UserAwardMap=userAwardMap;
	}	
	//生成商品销量成功时使用的构造器
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum,List<UserAwardMap> userAwardMapList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.UserAwardMapList=userAwardMapList;
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
	public UserAwardMap getUserAwardMap() {
		return UserAwardMap;
	}
	public void setUserAwardMap(UserAwardMap userAwardMap) {
		this.UserAwardMap = userAwardMap;
	}
	public List<UserAwardMap> getUserAwardMapList() {
		return UserAwardMapList;
	}
	public void setUserAwardMapList(List<UserAwardMap> userAwardMapList) {
		this.UserAwardMapList = userAwardMapList;
	}

	
}
