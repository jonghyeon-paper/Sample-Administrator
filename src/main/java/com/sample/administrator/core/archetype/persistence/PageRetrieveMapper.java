package com.sample.administrator.core.archetype.persistence;

import java.util.List;
import java.util.Map;

public interface PageRetrieveMapper<ENTITY> extends BaseMapper<ENTITY> {

	List<ENTITY> selectPage(Map<String, Object> parameterMap);
	int selectTotalCount(Map<String, Object> parameterMap);
}
