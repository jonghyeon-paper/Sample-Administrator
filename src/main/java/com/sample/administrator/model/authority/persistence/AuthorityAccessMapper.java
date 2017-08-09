package com.sample.administrator.model.authority.persistence;

import java.util.List;

import com.sample.administrator.model.authority.entity.AuthorityAccess;

public interface AuthorityAccessMapper {

	List<AuthorityAccess> selectList(AuthorityAccess authorityAccess);
	List<AuthorityAccess> selectListByAuthorityIds(String[] authorityIds);
	AuthorityAccess selectOne(AuthorityAccess authorityAccess);
	int insert(AuthorityAccess authorityAccess);
	int delete(AuthorityAccess authorityAccess);
}
