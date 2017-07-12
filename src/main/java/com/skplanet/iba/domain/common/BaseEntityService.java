package com.skplanet.iba.domain.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.iba.domain.user.User;
import com.skplanet.iba.framework.data.MapperParameter;
import com.skplanet.iba.framework.data.MapperParameter.Builder;
import com.skplanet.iba.framework.data.PagingContents;
import com.skplanet.iba.framework.data.PagingRequest;

public class BaseEntityService<ENTITY_TYPE extends BaseEntity, ENTITY_MAPPER extends BaseEntityMapper<ENTITY_TYPE>>  {
	@Autowired
	ENTITY_MAPPER entityMapper;

	public ENTITY_TYPE insert(User user, ENTITY_TYPE entity) {
		if (user != null) {
			entity.setRegDate(new Date());
			entity.setRegUserId(user.getRegUserId());
		}
		List<ENTITY_TYPE> entitys = new ArrayList<ENTITY_TYPE>();
		entitys.add(entity);
		entityMapper.insert(entitys);
		return entity;
	}
	
	public List<ENTITY_TYPE> insert(User user, List<ENTITY_TYPE> entities) {
		for (ENTITY_TYPE entity : entities) {
			if (user != null) {
				entity.setRegDate(new Date());
				entity.setRegUserId(user.getRegUserId());
			}
			entityMapper.insert(entities);
		}
		return entities;
	}
	
	public List<ENTITY_TYPE> selectList(User user, ENTITY_TYPE entity) {
		return entityMapper.selectList(entity);
	}

	public PagingContents<ENTITY_TYPE> selectPageList(User user, PagingRequest pagingRequest, ENTITY_TYPE entity) {
		Builder builder = MapperParameter.newBuilder();		
		builder.withPage(pagingRequest.getPage() - 1, pagingRequest.getCountPerPage());
		List<ENTITY_TYPE> entities = entityMapper.selectPageList(builder.build());
		return createPagingContents(user, builder.build(), pagingRequest, entities);
	}
	
	public ENTITY_TYPE selectOne(User user, ENTITY_TYPE entity) {
		return entityMapper.selectOne(entity);
	}
	
	public ENTITY_TYPE update(User user, ENTITY_TYPE entity) {
		if (user != null) {
			entity.setModDate(new Date());
			entity.setModUserId(user.getModUserId());
		}
		
		entityMapper.update(entity);
		return entity;
	}
	
	public void delete(User user, ENTITY_TYPE entity) {
		entityMapper.delete(entity);
	}
	
	protected PagingRequest createPageRequest(PagingRequest pagingRequest) {
		return new PagingRequest(pagingRequest.getPage() - 1, pagingRequest.getCountPerPage());
	}

	protected PagingContents<ENTITY_TYPE> createPagingContents(User user, MapperParameter mapperParameter, PagingRequest pagingRequest, List<ENTITY_TYPE> entities) {
		return createPagingContents(pagingRequest, getTotalPageByMapperParameter(user, pagingRequest.getCountPerPage(), mapperParameter), entities);
	}
	
	protected PagingContents<ENTITY_TYPE> createPagingContents(PagingRequest pagingRequest, int totalPage, List<ENTITY_TYPE> entities) {
		return new PagingContents<ENTITY_TYPE>(pagingRequest.getPage(), pagingRequest.getCountPerPage(), totalPage, entities);
	}
	
	protected int getTotalPage(User user, int countPerPage) {
		return getTotalPage(user, countPerPage, null);
	}

	protected <SEARCH_PARAMETER> int getTotalPage(User user, int countPerPage, SEARCH_PARAMETER searchParameter) {
		Builder builder = MapperParameter.newBuilder();
		builder.with("countPerPage", countPerPage);
		if (searchParameter != null) {
			builder.with(searchParameter);
		}
		return getTotalPageByMapperParameter(user, countPerPage, builder.build());
	}

	protected int getTotalPageByMapperParameter(User user, int countPerPage, MapperParameter mapperParameter) {
		int totalCount = entityMapper.selectTotalCount(mapperParameter);
		return (int) (totalCount / countPerPage) + (totalCount % countPerPage == 0 ? 0 : 1);
	}
}