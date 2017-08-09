package com.sample.administrator.model.user.persistence;

import java.util.List;

import com.sample.administrator.model.user.entity.UserAuthority;

public interface UserAuthorityMapper {

	List<UserAuthority> selectList(UserAuthority userAuthority);
	UserAuthority selectOne(UserAuthority userAuthority);
	int insert(UserAuthority userAuthority);
	int delete(UserAuthority userAuthority);
}
