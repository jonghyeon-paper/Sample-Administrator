package com.skplanet.iba.domain.code;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.iba.domain.common.BaseEntity;
import com.skplanet.iba.support.enumdata.UseState;

public class Code extends BaseEntity {

	private String codeId;
	private String parentCodeId;
	private String codeName;
	private String description;
	private Integer displayOrder;
	private UseState useState;
	
	private List<Code> childCode;
	
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getParentCodeId() {
		return parentCodeId;
	}
	public void setParentCodeId(String parentCodeId) {
		this.parentCodeId = parentCodeId;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public UseState getUseState() {
		return useState;
	}
	public void setUseState(UseState useState) {
		this.useState = useState;
	}
	public List<Code> getChildCode() {
		if (this.childCode == null) {
			this.childCode = new ArrayList<>();
		}
		return childCode;
	}
	public void setChildCode(List<Code> childCode) {
		this.childCode = childCode;
	}
	public void addChildCode(Code code) {
		if (this.childCode == null) {
			this.childCode = new ArrayList<>();
		}
		this.childCode.add(code);
	}
	public boolean hasChildCode() {
		return this.childCode != null && this.childCode.size() > 0;
	}
}
