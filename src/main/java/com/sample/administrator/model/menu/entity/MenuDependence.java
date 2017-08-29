package com.sample.administrator.model.menu.entity;

public class MenuDependence {

	private Integer sequence;
	private Integer menuId;
	private String dependenceUri;
	
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getDependenceUri() {
		return dependenceUri;
	}
	public void setDependenceUri(String dependenceUri) {
		this.dependenceUri = dependenceUri;
	}
}
