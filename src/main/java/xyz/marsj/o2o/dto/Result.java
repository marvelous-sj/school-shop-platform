package xyz.marsj.o2o.dto;

public class Result<T> {
	private T data;
	private boolean success;
	private int errCode;
	private String errMsg;
	public Result() {
	}
	//成功时构造器
	public Result(T data, boolean success) {
		super();
		this.data = data;
		this.success = success;
	}
	//失败时构造器
	public Result(boolean success, int errCode, String errMsg) {
		super();
		this.success = success;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
