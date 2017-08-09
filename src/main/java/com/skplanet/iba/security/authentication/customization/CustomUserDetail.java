package com.skplanet.iba.security.authentication.customization;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetail extends User {

	private static final long serialVersionUID = -8769437733457618615L;
	
	// DB에 저장하고 있는 사용자 정보를 넣을 객체
	private Object customUserData;

	public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, Object customUserData) {
		super(username, password, authorities);
		this.customUserData = customUserData;
	}

	public Object getCustomUserData() {
		return customUserData;
	}

	public void setCustomUserData(Object customUserData) {
		this.customUserData = customUserData;
	}

}
