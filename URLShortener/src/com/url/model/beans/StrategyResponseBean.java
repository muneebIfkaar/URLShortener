package com.url.model.beans;

public class StrategyResponseBean {

	private int statusCode;
	private String message;
	private Object responseData;
	
	public StrategyResponseBean(int statusCode, String msg, Object responseData) {
		this.statusCode = statusCode;
		this.message = msg;
		this.responseData = responseData;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResponseData() {
		return responseData;
	}
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
	
	
	
}
