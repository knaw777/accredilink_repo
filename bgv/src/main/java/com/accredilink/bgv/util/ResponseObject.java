package com.accredilink.bgv.util;

import java.io.Serializable;

public class ResponseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -107752139156246555L;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
