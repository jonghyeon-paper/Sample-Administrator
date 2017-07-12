package com.skplanet.iba.domain.authority;

import com.skplanet.iba.domain.common.BaseEntity;

public class AuthorityMenu extends BaseEntity {

	private String authorityId;
	private Integer menuId;
	
	public String getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}
