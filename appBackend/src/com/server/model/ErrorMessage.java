package com.server.model;

public class ErrorMessage {
	boolean result;
	String errorMessage;
	
	public ErrorMessage(boolean result, String errorMessage) {
		super();
		this.result = result;
		this.errorMessage = errorMessage;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public String toString() {
		return "ErrorMessage [result=" + result + ", errorMessage=" + errorMessage + "]";
	}
	
}
