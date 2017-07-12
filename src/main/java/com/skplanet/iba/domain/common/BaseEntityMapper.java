package com.skplanet.iba.domain.common;

import java.util.List;

import com.skplanet.iba.framework.data.MapperParameter;

public interface BaseEntityMapper<ENTITY_TYPE> {
	void insert(List<ENTITY_TYPE> entity);

	List<ENTITY_TYPE> selectList(ENTITY_TYPE entity);
	
	List<ENTITY_TYPE> selectPageList(MapperParameter mapperParameter);

	ENTITY_TYPE selectOne(ENTITY_TYPE entity);

	void update(ENTITY_TYPE entity);

	void delete(ENTITY_TYPE entity);

	int selectTotalCount(MapperParameter mapperParameter);
}
