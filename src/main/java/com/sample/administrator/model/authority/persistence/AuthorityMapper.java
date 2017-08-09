package com.sample.administrator.model.authority.persistence;

import java.util.List;

import com.sample.administrator.model.authority.entity.Authority;

public interface AuthorityMapper {

	List<Authority> selectList(Authority authority);
	Authority selectOne(Authority authority);
	int insert(Authority authority);
	int update(Authority authority);
	int delete(Authority authority);
}
