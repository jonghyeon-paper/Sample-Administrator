package com.skplanet.iba.framework.exception;

public class ErrorCodeException extends RuntimeException {
	private static final long serialVersionUID = 199982661982999817L;
	
	public static void throwErrorCode(boolean isThrow, ErrorCode errorCode) {
		if (isThrow) {
			throw new ErrorCodeException(errorCode);
		}
	}
	
	private ErrorCode errorCode;
	
	public ErrorCodeException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public ErrorCodeException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
	}
	
	public ErrorCodeException(int code, String message) {
		super(message);
		this.errorCode = new DefaultErrorCode(code, message);
	}
	
	public ErrorCodeException(int code, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = new DefaultErrorCode(code, message);
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}

