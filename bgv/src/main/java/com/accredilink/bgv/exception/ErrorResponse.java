package com.accredilink.bgv.exception;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {

	private LocalDateTime timeStamp;

	private String message;

	private Integer statusCode;

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

}
