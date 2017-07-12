package com.skplanet.iba.framework.exception;

public class DefaultErrorCode implements ErrorCode {
	private int code;
	private String message;
	
	public DefaultErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCodeValue() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}