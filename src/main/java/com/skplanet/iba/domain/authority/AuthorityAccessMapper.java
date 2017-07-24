package com.skplanet.iba.domain.authority;

import java.util.List;

public interface AuthorityAccessMapper {

	List<AuthorityAccess> selectList(AuthorityAccess authorityAccess);
	List<AuthorityAccess> selectListByAuthorityIds(String[] authorityIds);
	AuthorityAccess selectOne(AuthorityAccess authorityAccess);
	int insert(AuthorityAccess authorityAccess);
	int delete(AuthorityAccess authorityAccess);
}
