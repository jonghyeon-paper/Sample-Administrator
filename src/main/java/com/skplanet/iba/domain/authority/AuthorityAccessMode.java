package com.skplanet.iba.domain.authority;

import com.skplanet.iba.domain.authority.enumdata.AccessMode;
import com.skplanet.iba.domain.common.BaseEntity;

public class AuthorityAccessMode extends BaseEntity {

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
