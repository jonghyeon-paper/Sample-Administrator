package com.skplanet.iba.domain.authority;

import java.util.List;

public interface AuthorityAccessMapper {

	List<AuthorityAccess> selectList(AuthorityAccess authorityAccess);
	AuthorityAccess selectOne(AuthorityAccess authorityAccess);
	int insert(List<AuthorityAccess> authorityAccessList);
	int delete(AuthorityAccess authorityAccess);
}
