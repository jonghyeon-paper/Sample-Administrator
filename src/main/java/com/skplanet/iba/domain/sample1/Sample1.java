package com.skplanet.iba.domain.sample1;

import com.skplanet.iba.domain.common.BaseEntity;

public class Sample1 extends BaseEntity {
	private int sampleId;
	private String code;
	private String name;

	public int getSampleId() {
		return sampleId;
	}

	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
