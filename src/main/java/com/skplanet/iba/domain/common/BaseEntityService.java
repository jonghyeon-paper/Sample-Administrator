package com.skplanet.iba.domain.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.skplanet.iba.domain.user.User;
import com.skplanet.iba.framework.data.PageParameter;
import com.skplanet.iba.framework.data.PagingContents;
import com.skplanet.iba.framework.data.PagingRequest;

public class BaseEntityService<ENTITY_TYPE extends BaseEntity, ENTITY_MAPPER extends BaseEntityMapper<ENTITY_TYPE>> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityService.class);
	
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
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		parameterMap.put("pageParameter", new PageParameter(0, 0));
		if (entity != null) {
			String key = toCamelCase(entity.getClass().getSimpleName());
			parameterMap.put(key, entity);
		}
		
		return entityMapper.selectList(parameterMap);
	}

	public PagingContents<ENTITY_TYPE> selectList(User user, PagingRequest pagingRequest, ENTITY_TYPE entity) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		parameterMap.put("pageParameter", new PageParameter((pagingRequest.getPage() - 1) * pagingRequest.getCountPerPage(), pagingRequest.getCountPerPage()));
		if (entity != null) {
			String key = toCamelCase(entity.getClass().getSimpleName());
			parameterMap.put(key, entity);
		}
		List<ENTITY_TYPE> entities = entityMapper.selectList(parameterMap);
		
		return createPagingContents(user, parameterMap, pagingRequest, entities);
	}
	
	public PagingContents<ENTITY_TYPE> selectPage(PagingRequest pagingRequest, ENTITY_TYPE entity) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		PageParameter pageParameter = new PageParameter((pagingRequest.getPage() - 1) * pagingRequest.getCountPerPage(), pagingRequest.getCountPerPage());
		parameterMap.put("pageParameter", pageParameter);
		
		if (entity != null) {
			Pattern pattern = Pattern.compile("^get[\\w]{1}");
			Matcher matcher = null;
			Method[] methods = entity.getClass().getDeclaredMethods();
			for (Method method : methods) {
				matcher = pattern.matcher(method.getName().toString());
				if (matcher.find()) {
					try {
						String variableName = matcher.replaceAll(matcher.group().replaceAll("^get", "").toLowerCase());
						Object variableValue = method.invoke(entity);
						parameterMap.put(variableName, variableValue);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						//e.printStackTrace();
						LOGGER.error(e.getMessage());
						throw new RuntimeException("parameter bind exception!!");
					}
				}
			}
		}
		System.out.println("parameterMap > " + parameterMap.toString());
		List<ENTITY_TYPE> entities = entityMapper.selectPage(parameterMap);
		return createPagingContents(null, parameterMap, pagingRequest, entities);
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

	protected PagingContents<ENTITY_TYPE> createPagingContents(User user, Map<String, Object> parameterMap, PagingRequest pagingRequest, List<ENTITY_TYPE> entities) {
		return createPagingContents(pagingRequest, getTotalPageByMapperParameter(user, pagingRequest.getCountPerPage(), parameterMap), entities);
	}
	
	protected PagingContents<ENTITY_TYPE> createPagingContents(PagingRequest pagingRequest, int totalPage, List<ENTITY_TYPE> entities) {
		return new PagingContents<ENTITY_TYPE>(pagingRequest.getPage(), pagingRequest.getCountPerPage(), totalPage, entities);
	}
	
	protected int getTotalPageByMapperParameter(User user, int countPerPage, Map<String, Object> parameterMap) {
		int totalCount = entityMapper.selectTotalCount(parameterMap);
		return (int) (totalCount / countPerPage) + (totalCount % countPerPage == 0 ? 0 : 1);
	}
	
	private String toCamelCase(String target) {
		return StringUtils.uncapitalize(target);
	}
}