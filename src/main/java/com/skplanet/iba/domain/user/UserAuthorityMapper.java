package com.skplanet.iba.domain.user;

import java.util.List;

public interface UserAuthorityMapper {

	List<UserAuthority> selectList(UserAuthority userAuthority);
	UserAuthority selectOne(UserAuthority userAuthority);
	int insert(List<UserAuthority> userAuthorityList);
	int delete(UserAuthority userAuthority);
}
