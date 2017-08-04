package com.skplanet.iba.security.authentication.customization;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class CustomBadCredentialsException extends AuthenticationException {

	private String accessUserId;
	
	public CustomBadCredentialsException(String msg) {
		super(msg);
	}
	
	public CustomBadCredentialsException(String msg, String accessUserId) {
		super(msg);
		this.accessUserId = accessUserId;
	}
	
	public CustomBadCredentialsException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public CustomBadCredentialsException(String msg, String accessUserId, Throwable t) {
		super(msg, t);
		this.accessUserId = accessUserId;
	}

	public String getAccessUserId() {
		return accessUserId;
	}
	public void setAccessUserId(String accessUserId) {
		this.accessUserId = accessUserId;
	}

}
