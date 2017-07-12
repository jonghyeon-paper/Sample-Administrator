package com.skplanet.iba.framework.exception;

public enum DefaultDomainErrorCode implements ErrorCode {
	UNKNOWN_ERROR(9999, "keidas.common.unknown-error"),
	NO_CONTENTS(9100, "keidas.common.no-contents"),
	WRONG_RESOURCE_NO(9101, "keidas.common.wrong-resource-no"),
	NOT_SUPPORT_OPERATION(9102, "keidas.common.not-support-operation"),
	
	RESOURCE_NOT_FOUND(1000, "keidas.domain.resource-not-found"),
    ;

	private int errorCode;
	private String message;

	DefaultDomainErrorCode(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	@Override
	public int getCodeValue() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
