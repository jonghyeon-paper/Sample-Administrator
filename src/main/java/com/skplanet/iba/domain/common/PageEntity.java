package com.skplanet.iba.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PageEntity extends BaseEntity {
	
	@JsonIgnore
	private Integer start;
	@JsonIgnore
	private Integer offset;
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
}
