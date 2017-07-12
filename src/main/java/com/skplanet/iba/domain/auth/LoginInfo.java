package com.skplanet.iba.domain.auth;

import com.skplanet.iba.domain.user.User;

public class LoginInfo {
	private User user;
	
	public LoginInfo(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
