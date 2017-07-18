package com.skplanet.iba.domain.menu;

import java.util.List;

public interface MenuDependenceMapper {

	List<MenuDependence> selectList(MenuDependence menuDependence);
	MenuDependence selectOne(MenuDependence menuDependence);
	int insert(MenuDependence menuDependence);
	int update(MenuDependence menuDependence);
	int delete(MenuDependence menuDependence);
}
