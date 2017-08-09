package com.sample.administrator.model.authority.persistence;

import java.util.List;

import com.sample.administrator.model.authority.entity.AuthorityMenu;

public interface AuthorityMenuMapper {

	List<AuthorityMenu> selectList(AuthorityMenu authorityMenu);
	List<AuthorityMenu> selectListByAuthorityIds(String... authorityIds);
	List<AuthorityMenu> selectListByMenuIds(Integer... menuIds);
	AuthorityMenu selectOne(AuthorityMenu authorityMenu);
	int insert(AuthorityMenu authorityMenu);
	int delete(AuthorityMenu authorityMenu);
}
