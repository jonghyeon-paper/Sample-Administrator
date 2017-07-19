package com.skplanet.iba.domain.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.skplanet.iba.domain.common.BaseEntity;
import com.skplanet.iba.support.enumdata.UseState;

public class Menu extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5063771347758618898L;
	
	private Integer menuId;
	private Integer parentMenuId;
	private String menuName;
	private String uri;
	private String description;
	private UseState useState;
	
	private List<MenuDependence> menuDependenceList;
	
	private List<Menu> childMenu;
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UseState getUseState() {
		return useState;
	}
	public void setUseState(UseState useState) {
		this.useState = useState;
	}
	public List<MenuDependence> getMenuDependenceList() {
		return menuDependenceList;
	}
	public void setMenuDependenceList(List<MenuDependence> menuDependenceList) {
		this.menuDependenceList = menuDependenceList;
	}
	public List<Menu> getChildMenu() {
		if (this.childMenu == null) {
			this.childMenu = new ArrayList<>();
		}
		return childMenu;
	}
	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}
	public void addChildMenu(Menu menu) {
		if (this.childMenu == null) {
			this.childMenu = new ArrayList<>();
		}
		this.childMenu.add(menu);
	}
	public boolean hasChildMenu() {
		return this.childMenu != null && this.childMenu.size() > 0;
	}
}
