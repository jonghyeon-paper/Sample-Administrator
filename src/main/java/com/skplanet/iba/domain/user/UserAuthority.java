package com.skplanet.iba.domain.user;

import java.io.Serializable;

import com.skplanet.iba.domain.common.BaseEntity;

public class UserAuthority extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 629105044498044051L;
	
	private String userId;
	private String authorityId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}
	
}
