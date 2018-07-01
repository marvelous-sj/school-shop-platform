package xyz.marsj.o2o.dto;

import java.util.List;

import xyz.marsj.o2o.entity.UserProductMap;
import xyz.marsj.o2o.enums.UserProductMapStateEnum;

public class UserProductMapExecution {
	private int state;
	private String stateInfo;
	private int count;
	private UserProductMap UserProductMap;
	private List<UserProductMap> UserProductMapList;
	public UserProductMapExecution() {
		
	}
	//生成商品销量失败时使用的构造器
	public UserProductMapExecution(UserProductMapStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//生成商品销量成功时使用的构造器
	public UserProductMapExecution(UserProductMapStateEnum stateEnum,UserProductMap userProductMap) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.UserProductMap=userProductMap;
	}	
	//生成商品销量成功时使用的构造器
	public UserProductMapExecution(UserProductMapStateEnum stateEnum,List<UserProductMap> userProductMapList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.UserProductMapList=userProductMapList;
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
	public UserProductMap getUserProductMap() {
		return UserProductMap;
	}
	public void setUserProductMap(UserProductMap userProductMap) {
		this.UserProductMap = userProductMap;
	}
	public List<UserProductMap> getUserProductMapList() {
		return UserProductMapList;
	}
	public void setUserProductMapList(List<UserProductMap> userProductMapList) {
		this.UserProductMapList = userProductMapList;
	}

	
}
