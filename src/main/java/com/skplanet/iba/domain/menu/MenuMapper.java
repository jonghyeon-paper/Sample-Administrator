package com.skplanet.iba.domain.menu;

import java.util.List;

public interface MenuMapper {

	List<Menu> selectList(Menu menu);
	Menu selectOne(Menu menu);
	int insert(List<Menu> menuList);
	int update(Menu menu);
	int delete(Menu menu);
}