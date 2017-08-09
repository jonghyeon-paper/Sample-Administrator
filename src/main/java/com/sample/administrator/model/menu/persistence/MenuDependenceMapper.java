package com.sample.administrator.model.menu.persistence;

import java.util.List;

import com.sample.administrator.model.menu.entity.MenuDependence;

public interface MenuDependenceMapper {

	List<MenuDependence> selectList(MenuDependence menuDependence);
	MenuDependence selectOne(MenuDependence menuDependence);
	int insert(MenuDependence menuDependence);
	int update(MenuDependence menuDependence);
	int delete(MenuDependence menuDependence);
}
