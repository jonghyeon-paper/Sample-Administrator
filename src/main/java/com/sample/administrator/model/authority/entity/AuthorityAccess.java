package com.sample.administrator.model.authority.entity;

import com.sample.administrator.core.archetype.entity.BaseEntity;
import com.sample.administrator.model.authority.entity.element.AccessMode;

public class AuthorityAccess extends BaseEntity {

	private String authorityId;
	private AccessMode accessMode;
	
	public String getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}
	public AccessMode getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(AccessMode accessMode) {
		this.accessMode = accessMode;
	}
	
}
