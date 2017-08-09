package com.sample.administrator.core.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -1294278423347213429L;

	public BusinessException(String message) {
		super(message);
	}
}
