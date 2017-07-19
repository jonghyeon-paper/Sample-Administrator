package com.skplanet.iba.domain.code;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.support.enumdata.UseState;

@Service
public class CodeService {

	@Autowired
	private CodeMapper codeMapper;
	
	public Code retrieve(Code code) {
		return codeMapper.selectOne(code);
	}
	
	public List<Code> retrieveList(Code code) {
		return codeMapper.selectList(code);
	}
	
	@Transactional
	public Boolean add(Code code) {
		// 등록,수정자 id 설정
		int insertCount = codeMapper.insert(code);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean add(List<Code> codeList) {
		Boolean flag = true;
		for (Code code : codeList) {
			flag = flag && this.add(code);
		}
		return flag;
	}
	
	@Transactional
	public Boolean edit(Code code) {
		// 수정자 id 설정
		int updateCount = codeMapper.update(code);
		return updateCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean remove(Code code) {
		int deleteCount = codeMapper.delete(code);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeByCodeId(String codeId) {
		Code code = new Code();
		code.setCodeId(codeId);
		return this.remove(code);
	}
	
	@Transactional
	public Boolean removeByPrimaryKey(String codeId) {
		Code code = new Code();
		code.setCodeId(codeId);
		return this.remove(code);
	}
	
	public Code getCodeHierarchy(Code code) {
		List<Code> codeList = this.retrieveList(code);
		return createCodeHierarchy(codeList);
	}
	
	public Code createCodeHierarchy(List<Code> codeList) {
		Code dummyTop = new Code();
		//dummyTop.setCodeId("-1");
		//dummyTop.setParentCodeId("-1");
		dummyTop.setCodeName("TOP");
		dummyTop.setDescription("최상위 코드");
		dummyTop.setUseState(UseState.USE);
		for (int i = codeList.size() - 1; i > -1; i--) {
			Code temporary = codeList.get(i);
			if (temporary.getParentCodeId() != null && !"".equals(temporary.getParentCodeId())) {
				for (int j = codeList.size() - 2; j > -1; j--) {
					Code target = codeList.get(j);
					if (target.getCodeId().equals(temporary.getParentCodeId())) {
						target.getChildCode().add(temporary);
						break;
					}
				}
			} else {
				dummyTop.getChildCode().add(temporary);
			}
			codeList.remove(i);
		}
		sortCodeHierarchy(dummyTop);
		return dummyTop;
	}
	
	private void sortCodeHierarchy(Code code) {
		if (code.hasChildCode()) {
			List<Code> codeList = code.getChildCode();
			List<Code> sortedCodeList = new ArrayList<>();
			for (int i = codeList.size() - 1; i > -1; i--) {
				sortCodeHierarchy(codeList.get(i));
				sortedCodeList.add(codeList.get(i));
			}
			code.setChildCode(sortedCodeList);
		}
	}
}
