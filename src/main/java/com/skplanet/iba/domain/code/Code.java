package com.skplanet.iba.domain.code;

import com.skplanet.iba.domain.common.BaseEntity;
import com.skplanet.iba.share.enumdata.UseState;

public class Code extends BaseEntity {

	String codeId;
	String parentCodeId;
	String codeName;
	String description;
	UseState useState;
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
	public UseState getUseState() {
		return useState;
	}
	public void setUseState(UseState useState) {
		this.useState = useState;
	}
	
}
