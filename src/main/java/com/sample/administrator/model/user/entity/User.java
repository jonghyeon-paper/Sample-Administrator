package com.sample.administrator.model.user.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.administrator.core.archetype.entity.BaseEntity;
import com.sample.administrator.model.menu.entity.Menu;
import com.sample.administrator.web.element.UseState;

public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6131414886995573263L;
	
	private String userId;
	private String userName;
	private UseState useState;
	
	private List<UserAuthority> userAuthorityList;
	
	@JsonIgnore
	private Menu accessibleMenu;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public UseState getUseState() {
		return useState;
	}
	public void setUseState(UseState useState) {
		this.useState = useState;
	}
	public List<UserAuthority> getUserAuthorityList() {
		return userAuthorityList;
	}
	public void setUserAuthorityList(List<UserAuthority> userAuthorityList) {
		this.userAuthorityList = userAuthorityList;
	}
	public Menu getAccessibleMenu() {
		return accessibleMenu;
	}
	public void setAccessibleMenu(Menu accessibleMenu) {
		this.accessibleMenu = accessibleMenu;
	}
}
