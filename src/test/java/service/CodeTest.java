package service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.code.Code;
import com.skplanet.iba.domain.code.CodeService;
import com.skplanet.iba.support.enumdata.UseState;

public class CodeTest extends AbstractJUnit {
	
	static final String T0000 = "T0000";
	static final String T0001 = "T0001";
	static final String T0002 = "T0002";
	static final String T0003 = "T0003";

	@Autowired
	private CodeService codeuService;
	
	//@Test
	@Transactional
	public void add() throws Exception {
		Code systemCode = new Code();
		systemCode.setCodeId(T0000);
		systemCode.setCodeName("테스트 코드");
		systemCode.setUseState(UseState.USE);
		
		codeuService.addCode(systemCode);
		
		Code sampleCode1 = new Code();
		sampleCode1.setCodeId(T0001);
		sampleCode1.setCodeName("sample1");
		sampleCode1.setUseState(UseState.USE);
		sampleCode1.setParentCodeId(systemCode.getCodeId());
		
		Code sampleCode2 = new Code();
		sampleCode2.setCodeId(T0002);
		sampleCode2.setCodeName("sample2");
		sampleCode2.setUseState(UseState.USE);
		sampleCode2.setParentCodeId(systemCode.getCodeId());
		
		Code sampleCode3 = new Code();
		sampleCode3.setCodeId(T0003);
		sampleCode3.setCodeName("sample3");
		sampleCode3.setUseState(UseState.USE);
		sampleCode3.setParentCodeId(systemCode.getCodeId());
		
		List<Code> codeList = new ArrayList<>();
		codeList.add(sampleCode1);
		codeList.add(sampleCode2);
		codeList.add(sampleCode3);
		
		codeuService.addCode(codeList);
		
		List<Code> resultList = codeuService.retrieveList(new Code());
		print(resultList);
	}
	
	//@Test
	public void edit() {
		Code code = new Code();
		code.setCodeId(T0000);
		code.setCodeName("sample99999");
		code.setUseState(UseState.UNUSE);
		
		codeuService.editCode(code);
		
		Code condition = new Code();
		condition.setCodeId(T0000);
		Code resultCode = codeuService.retrieveCode(condition);
		print(resultCode);
	}
	
	//@Test
	public void remove() {
		codeuService.removeCodeByCodeId(T0003);
		codeuService.removeCodeByCodeId(T0002);
		codeuService.removeCodeByCodeId(T0001);
		codeuService.removeCodeByCodeId(T0000);
	}
	
	@Test
	public void hierarchy() {
		Code result = codeuService.getHierarchyCode(new Code());
		print(result);
	}
}
