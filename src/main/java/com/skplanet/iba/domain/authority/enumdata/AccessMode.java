package com.skplanet.iba.domain.authority.enumdata;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccessMode {
	READ("읽기", 1),
	WRITE("쓰기", 2);
	
	private final String description;
	private final int grade; // 등급은 높을 수 록 큰 권한.
	
	private AccessMode(String description, int grade) {
		this.description = description;
		this.grade = grade;
	}

	public String getDescription() {
		return description;
	}
	
	public int getGrade() {
		return grade;
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
