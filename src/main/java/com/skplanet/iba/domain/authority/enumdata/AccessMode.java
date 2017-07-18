package com.skplanet.iba.domain.authority.enumdata;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccessMode {
	READ("읽기"),
	WRITE("쓰기"),
	EXCUTE("실행");
	
	private final String description;
	
	private AccessMode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	@JsonCreator
	public static AccessMode createAccessMode(String name) {
		for (AccessMode item : AccessMode.values()) {
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
