package com.skplanet.iba.domain.code;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CodeService {

	@Autowired
	private CodeMapper codeMapper;
	
	public Code retrieveCode(Code code) {
		return codeMapper.selectOne(code);
	}
	
	public List<Code> retrieveList(Code code) {
		return codeMapper.selectList(code);
	}
	
	@Transactional
	public Boolean addCode(List<Code> codeList) {
		// 등록,수정자 id 설정
		int insertCount = codeMapper.insert(codeList);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean addCode(Code code) {
		List<Code> codeList = new ArrayList<>();
		codeList.add(code);
		return this.addCode(codeList);
	}
	
	@Transactional
	public Boolean editCode(Code code) {
		// 수정자 id 설정
		int updateCount = codeMapper.update(code);
		return updateCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeCode(Code code) {
		int deleteCount = codeMapper.delete(code);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeCodeByCodeId(String codeId) {
		Code code = new Code();
		code.setCodeId(codeId);
		return this.removeCode(code);
	}
	
	@Transactional
	public Boolean removeMenuByPrimaryKey(String codeId) {
		Code code = new Code();
		code.setCodeId(codeId);
		return this.removeCode(code);
	}
}
