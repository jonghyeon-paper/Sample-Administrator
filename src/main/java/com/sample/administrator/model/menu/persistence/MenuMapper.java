package com.sample.administrator.model.menu.persistence;

import java.util.List;

import com.sample.administrator.model.menu.entity.Menu;

public interface MenuMapper {

	List<Menu> selectList(Menu menu);
	List<Menu> selectListByMenuIds(Integer... menuIds);
	Menu selectOne(Menu menu);
	int insert(Menu menu);
	int update(Menu menu);
	int delete(Menu menu);
}
