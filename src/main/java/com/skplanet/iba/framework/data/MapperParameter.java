package com.skplanet.iba.framework.data;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.util.StringUtils;

public class MapperParameter extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 2521975088325048592L;
	
	public static final class Builder {
		private MapperParameter instance;

		private Builder() {
			instance = new MapperParameter();
		}
		
		public <ENTITY_ID> Builder withId(ENTITY_ID id) {
			instance.put("id", id);
			return this;
		}
		
		public <ENTITY_ID> Builder withIds(List<ENTITY_ID> ids) {
			instance.put("ids", ids);
			return this;
		}
		
		public Builder with(Object value) {
			if (value != null) {
				String key = toCamelCase(value.getClass().getSimpleName());
				instance.put(key, value);
			}
			return this;
		}
		
		public Builder withObject(String key, Object value) {
			instance.put(key, value);
			return this;
		}
		
		public Builder withPage(int page, int countPerPage) {
			return withPage(page, countPerPage, false);
		}
		
		public Builder withPage(int page, int countPerPage, boolean isZeroStart) {
			instance.put("pageParameter", new PageParameter(isZeroStart ? page - 1 * countPerPage : page * countPerPage, countPerPage));
			return this;
		}
		
		public Builder with(String key, String value) {
			instance.put(key, value);
			return this;
		}
		
		public Builder with(String key, int value) {
			instance.put(key, value);
			return this;
		}
		
		public Builder with(String key, long value) {
			instance.put(key, value);
			return this;
		}
		
		public Builder with(String key, float value) {
			instance.put(key, value);
			return this;
		}
		
		public Builder with(String key, Date value) {
			instance.put(key, value);
			return this;
		}
		
		public <VALUE_TYPE> Builder with(String key, List<VALUE_TYPE> values) {
			instance.put(key, values);
			return this;
		}

		public MapperParameter build() {
			return instance;
		}

		private String toCamelCase(String target) {
			return StringUtils.uncapitalize(target);
		}
	}

	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static MapperParameter buildWithPageParam(int page, int countPerPage) {
		return newBuilder().withPage(page, countPerPage).build();
	}
	
	public static MapperParameter buildWithObject(Object value) {
		return newBuilder().with(value).build();
	}

	private MapperParameter() {
	}
}
