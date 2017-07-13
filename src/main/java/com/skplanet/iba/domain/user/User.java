package com.skplanet.iba.domain.user;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skplanet.iba.domain.common.PageEntity;
import com.skplanet.iba.domain.menu.Menu;
import com.skplanet.iba.share.enumdata.UseState;

public class User extends PageEntity implements Serializable {

	private static final long serialVersionUID = 6131414886995573263L;
	
	private String userId;
	private String userName;
	private UseState useState;
	
	@JsonIgnore
	private List<UserAuthority> userAuthorityList;
	
	@JsonIgnore
	private Menu accessableMenu;
	
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
	public Menu getAccessableMenu() {
		return accessableMenu;
	}
	public void setAccessableMenu(Menu accessableMenu) {
		this.accessableMenu = accessableMenu;
	}
}
