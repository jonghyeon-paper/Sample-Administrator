package com.skplanet.iba.domain.authority;

import java.util.List;

public interface AuthorityAccessModeMapper {

	List<AuthorityAccessMode> selectList(AuthorityAccessMode authorityAccessMode);
	AuthorityAccessMode selectOne(AuthorityAccessMode authorityAccessMode);
	int insert(List<AuthorityAccessMode> authorityAccessModeList);
	int delete(AuthorityAccessMode authorityAccessMode);
}
