package com.skplanet.iba.domain.user;

import java.util.List;

import com.skplanet.iba.domain.common.BaseEntity;
import com.skplanet.iba.domain.menu.Menu;
import com.skplanet.iba.share.enumdata.UseState;

public class User extends BaseEntity {

	private String userId;
	private String userName;
	private UseState useState;
	
	private List<UserAuthority> userAuthorityList;
	
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
