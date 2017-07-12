package com.skplanet.iba.domain.common;

import java.util.Date;

public class BaseEntity {
	private Date regDate;
	private int regUserId;
	private Date modDate;
	private int modUserId;
	
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(int regUserId) {
		this.regUserId = regUserId;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public int getModUserId() {
		return modUserId;
	}

	public void setModUserId(int modUserId) {
		this.modUserId = modUserId;
	}
}