package com.sample.administrator.core.archetype;

import java.util.List;

public interface BaseService<ENTITY> {

	ENTITY retrieve(ENTITY entity);
	List<ENTITY> retrieveList(ENTITY entity);
	Boolean add(ENTITY entity);
	Boolean edit(ENTITY entity);
	Boolean remove(ENTITY entity);
}
