package com.skplanet.iba.domain.login;

public class LoginHistory {
	
	private Integer sequence;
	private String accessUserId;
	private String accessDate;
	private Boolean accessSuccess;
	private String message;
	
	public LoginHistory() {
	}
	
	public LoginHistory(String accessUserId, Boolean accessSuccess, String message) {
		this.accessUserId = accessUserId;
		this.accessSuccess = accessSuccess;
		this.message = message;
	}
	
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getAccessUserId() {
		return accessUserId;
	}
	public void setAccessUserId(String accessUserId) {
		this.accessUserId = accessUserId;
	}
	public String getAccessDate() {
		return accessDate;
	}
	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}
	public Boolean getAccessSuccess() {
		return accessSuccess;
	}
	public void setAccessSuccess(Boolean accessSuccess) {
		this.accessSuccess = accessSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
