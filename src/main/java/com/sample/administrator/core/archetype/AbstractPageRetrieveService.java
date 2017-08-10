package com.sample.administrator.core.archetype;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sample.administrator.core.archetype.entity.PagingContents;
import com.sample.administrator.core.archetype.entity.PageRequest;
import com.sample.administrator.core.archetype.persistence.PageRetrieveMapper;

public abstract class AbstractPageRetrieveService<ENTITY, MAPPER extends PageRetrieveMapper<ENTITY>> extends AbstractBaseService<ENTITY> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPageRetrieveService.class);
	
	@Autowired
	private MAPPER entityMapper;
	
	public PagingContents<ENTITY> retrievePage(ENTITY entity, PageRequest pagingRequest) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("start", pagingRequest.getStartIndex());
		parameterMap.put("offset", pagingRequest.getCountPerPage());
		
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
		
		List<ENTITY> contents = entityMapper.selectPage(parameterMap);
		int totalCount = entityMapper.selectTotalCount(parameterMap);
		return new PagingContents<ENTITY>(pagingRequest.getPage(), pagingRequest.getCountPerPage(), contents, totalCount);
	}
}