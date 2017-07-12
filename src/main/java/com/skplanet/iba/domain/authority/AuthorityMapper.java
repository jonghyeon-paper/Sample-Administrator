package com.skplanet.iba.domain.authority;

import java.util.List;

public interface AuthorityMapper {

	List<Authority> selectList(Authority authority);
	Authority selectOne(Authority authority);
	int insert(List<Authority> authorityList);
	int update(Authority authority);
	int delete(Authority authority);
}
