package com.skplanet.iba.domain.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseEntity {
	private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private Date regDate;
	private String regUserId;
	private Date modDate;
	private String modUserId;
	
	public String getRegDate() {
		String stringDate = "";
		if (regDate != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
			stringDate = simpleDateFormat.format(regDate);
		}
		return stringDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public String getModUserId() {
		return modUserId;
	}
	public void setModUserId(String modUserId) {
		this.modUserId = modUserId;
	}
	
}