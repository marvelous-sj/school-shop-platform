package xyz.marsj.o2o.dto;

import java.util.List;

import xyz.marsj.o2o.entity.UserShopMap;
import xyz.marsj.o2o.enums.UserShopMapStateEnum;

public class UserShopMapExecution {
	private int state;
	private String stateInfo;
	private int count;
	private UserShopMap UserShopMap;
	private List<UserShopMap> UserShopMapList;
	public UserShopMapExecution() {
		
	}
	//生成商品销量失败时使用的构造器
	public UserShopMapExecution(UserShopMapStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//生成商品销量成功时使用的构造器
	public UserShopMapExecution(UserShopMapStateEnum stateEnum,UserShopMap userShopMap) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.UserShopMap=userShopMap;
	}	
	//生成商品销量成功时使用的构造器
	public UserShopMapExecution(UserShopMapStateEnum stateEnum,List<UserShopMap> userShopMapList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.UserShopMapList=userShopMapList;
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
	public UserShopMap getUserShopMap() {
		return UserShopMap;
	}
	public void setUserShopMap(UserShopMap userShopMap) {
		this.UserShopMap = userShopMap;
	}
	public List<UserShopMap> getUserShopMapList() {
		return UserShopMapList;
	}
	public void setUserShopMapList(List<UserShopMap> userShopMapList) {
		this.UserShopMapList = userShopMapList;
	}

	
}
