package com.sample.administrator.core.archetype;

import java.util.List;

public abstract class AbstractBaseService<ENTITY> {

	public abstract ENTITY retrieve(ENTITY entity);
	public abstract List<ENTITY> retrieveList(ENTITY entity);
	public abstract Boolean add(ENTITY entity);
	public abstract Boolean add(List<ENTITY> entityList);
	public abstract Boolean edit(ENTITY entity);
	public abstract Boolean remove(ENTITY entity);
}
