package com.skplanet.iba.domain.common;

import java.util.List;
import java.util.Map;

public interface BaseEntityMapper<ENTITY_TYPE> {

	List<ENTITY_TYPE> selectList(Map<String, Object> parameterMap);
	List<ENTITY_TYPE> selectList(ENTITY_TYPE entity);
	List<ENTITY_TYPE> selectPage(Map<String, Object> parameterMap);
	int selectTotalCount(Map<String, Object> parameterMap);
	ENTITY_TYPE selectOne(ENTITY_TYPE entity);
	int insert(List<ENTITY_TYPE> entity);
	int update(ENTITY_TYPE entity);
	int delete(ENTITY_TYPE entity);
}
