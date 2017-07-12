package com.skplanet.iba.share.enumdata;

public enum ResponseCode {
	SUCCESS("성공하였습니다."),
	FAIL("실패하였습니다.");
	
	private final String description;
	
	private ResponseCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
