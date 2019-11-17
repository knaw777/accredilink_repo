package com.accredilink.bgv.exception;

public class CustomException extends RuntimeException {
	
	private static final long serialVersionUID = -5483466936177734759L;
	private String exceptionMessge;

	public CustomException(String exceptionMessge) {
		super();
		this.exceptionMessge = exceptionMessge;
	}

	public String getExceptionMessge() {
		return exceptionMessge;
	}

	public void setExceptionMessge(String exceptionMessge) {
		this.exceptionMessge = exceptionMessge;
	}
	
	

}
