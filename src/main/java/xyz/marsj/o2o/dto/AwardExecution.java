package xyz.marsj.o2o.dto;

import java.util.List;

import xyz.marsj.o2o.entity.Award;
import xyz.marsj.o2o.enums.AwardStateEnum;

public class AwardExecution {
	private int state;
	private String stateInfo;
	private int count;
	private Award Award;
	private List<Award> AwardList;
	public AwardExecution() {
		
	}
	//生成商品销量失败时使用的构造器
	public AwardExecution(AwardStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//生成商品销量成功时使用的构造器
	public AwardExecution(AwardStateEnum stateEnum,Award Award) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.Award=Award;
	}	
	//生成商品销量成功时使用的构造器
	public AwardExecution(AwardStateEnum stateEnum,List<Award> AwardList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.AwardList=AwardList;
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
	public Award getAward() {
		return Award;
	}
	public void setAward(Award Award) {
		this.Award = Award;
	}
	public List<Award> getAwardList() {
		return AwardList;
	}
	public void setAwardList(List<Award> AwardList) {
		this.AwardList = AwardList;
	}

	
}
