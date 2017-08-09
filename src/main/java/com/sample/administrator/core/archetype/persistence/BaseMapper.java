package com.sample.administrator.core.archetype.persistence;

import java.util.List;

public interface BaseMapper<ENTITY> {

	List<ENTITY> selectList(ENTITY entity);
	ENTITY selectOne(ENTITY entity);
	int insert(ENTITY entity);
	int update(ENTITY entity);
	int delete(ENTITY entity);
}
