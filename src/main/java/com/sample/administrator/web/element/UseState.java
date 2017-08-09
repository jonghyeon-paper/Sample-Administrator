package com.sample.administrator.web.element;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sample.administrator.core.mybatis.typehandler.StoredValue;

public enum UseState {
	USE("사용"),
	UNUSE("미사용");
	
	private String description;
	
	private UseState(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	@StoredValue
	public String getStoredValue() {
		return this.name();
	}
	
	@JsonCreator
	public static UseState createUseState(String name) {
		for (UseState item : UseState.values()) {
			if (name.equals(item.name())) {
				return item;
			}
		}
		return null;
	}
	
	@JsonValue
	public Map<String, String> getJson() {
		Map<String, String> json = new HashMap<>();
		json.put("displayValue", this.name());
		json.put("description", this.description);
		return json;
	}
}
