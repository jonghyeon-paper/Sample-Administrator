package com.skplanet.iba.framework.data;

import java.util.List;
import java.util.Map;

public class SortParameter {	
	private List<Map<String, String>> sort;

	public List<Map<String, String>> getSort() {
		return sort;
	}

	public void setSort(List<Map<String, String>> sort) {
		this.sort = sort;
	}
	
	public String getSortField() {
		return sort != null ? sort.get(0).get("field") : null;
	}
	
	public String getSortDirection() {
		return sort != null ? sort.get(0).get("direction") : null;
	}
}
