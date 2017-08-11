package com.sample.administrator.model.code;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.administrator.model.code.entity.Code;
import com.sample.administrator.model.code.persistence.CodeMapper;
import com.sample.administrator.security.utility.SecurityUtility;
import com.sample.administrator.web.element.UseState;

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
		code.setRegUserId(SecurityUtility.getLoginUserId());
		code.setModUserId(SecurityUtility.getLoginUserId());
		
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
		code.setModUserId(SecurityUtility.getLoginUserId());
		
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
	
	/**
	 * 계층구조 오브젝트 생성
	 * @param codeList
	 * @return
	 */
	public Code createCodeHierarchy(List<Code> codeList) {
		Code dummyTop = new Code();
		//dummyTop.setCodeId("-1");
		//dummyTop.setParentCodeId("-1");
		dummyTop.setCodeName("TOP");
		dummyTop.setDescription("최상위 코드");
		dummyTop.setUseState(UseState.USE);
		
		// 계층 구조를 찾아가기 위한 맵
		//- 위에서 아래로 찾아가는 top > down구조로 정의됨.
		//- queue데이터로 top > down으로 데이터를 넣고, top > down으로 데이터를 꺼낸다.
		Map<String, Queue<String>> hierarchyMap = new HashMap<>();
		
		// 최상위 메뉴 등록
		//- 더미 객체에 최상위 레벨의 메뉴를 자식으로 추가한다.
		for (int i = 0; i < codeList.size() ; i++) {
			Code item = codeList.get(i);
			if ("".equals(item.getParentCodeId())) {
				// 하위에 등록한다.
				dummyTop.addChildCode(item);
				
				// 계층 구조를 맵에 추가한다.
				//- 1레벨
				Queue<String> temp = new LinkedList<>();
				temp.add(item.getCodeId());
				hierarchyMap.put(item.getCodeId(), temp);
				
				// 사용 된 데이터는 list에서 제거한다.
				codeList.remove(i);
				i--;
			}
		}
		
		// 하위 메뉴 등록
		//- list의 값이 존재하면 반복해서 실행된다.
		//- 마지막 루프 사이즈값이 목록의 사이즈보다 크면 반복 실행한다.(부모가 없는 경우 무한루프)
		int lastLoopSize = codeList.size() + 1;
		while (codeList.size() > 0 && codeList.size() < lastLoopSize) {
			lastLoopSize = codeList.size();
			
			for (int i = 0; i < codeList.size() ; i++) {
				Code item = codeList.get(i);
				
				// 계층 구조맵에 부모값이 있으면 찾아간다.
				//- 자기 자신의 계층을 계층 구조맵에 등록.
				//- 부모를 찾아가서 하위에 등록.
				//- list에서 자신을 제거.
				if (hierarchyMap.containsKey(item.getParentCodeId())) {
					String parentId = item.getParentCodeId();
					
					// 계층 구조를 맵에 추가한다.
					//- n레벨
					Queue<String> newHierarchyMap = new LinkedList<>(hierarchyMap.get(parentId)); // 새 인스턴스
					newHierarchyMap.add(item.getCodeId());
					hierarchyMap.put(item.getCodeId(), newHierarchyMap);
					
					// 하위에 등록한다.
					//- n레벨의 부모를 찾아가기 위해 재귀호출한다.
					Queue<String> parentHierarchyMap = new LinkedList<>(hierarchyMap.get(parentId)); // 새 인스턴스
					findObjectAndAdd(item, parentHierarchyMap, dummyTop.getChildCode());
					
					//사용 된 데이터는 list에서 제거한다.
					codeList.remove(i);
					i--;
				}
			}
		}
		
		
		sortCodeHierarchy(dummyTop);
		return dummyTop;
	}
	
	/**
	 * 계층 구조를 찾는다
	 * @param targetItem - 대상 오브젝트
	 * @param hierarchyMap - 계층 구조맵
	 * @param list - 대상 목록
	 */
	private void findObjectAndAdd(Code targetItem, Queue<String> hierarchyMap, List<Code> list) {
		// 계층 구조맵에서 맨 위의 값을 꺼낸다.
		String findCodeId = hierarchyMap.poll();
		Code parentCode = null;
		
		// 대상 목록에서 일치하는 대상을 찾는다.
		for (Code code : list) {
			if (code.getCodeId().equals(findCodeId)) {
				parentCode = code;
			}
		}
		
		if (parentCode != null) {
			if (hierarchyMap.size() == 0) {
				// 계층 구조맵이 없다면 대상 오브젝트를 하위에 추가한다.
				parentCode.addChildCode(targetItem);
			} else {
				// 계층 구조맵이 있다면 재귀호출.
				findObjectAndAdd(targetItem, hierarchyMap, parentCode.getChildCode());
			}
		}
	}
	
	/**
	 * 순서값을 사용한 정렬
	 * - 선택 정렬 알고리즘.
	 * - 순서값이 없는 데이터는 아무동작 안함.
	 * @param code
	 */
	private void sortCodeHierarchy(Code code) {	
		if (code.hasChildCode()) {
			List<Code> codeList = code.getChildCode();
			for (int i = 0; i < codeList.size(); i++) {
				if (codeList.get(i).getDisplayOrder() == null) {
					continue;
				}
				int lowestValueIndex = i;
				for (int j = i + 1; j < codeList.size(); j++) {
					if (codeList.get(j).getDisplayOrder() == null) {
						continue;
					}
					if (codeList.get(j).getDisplayOrder() < codeList.get(lowestValueIndex).getDisplayOrder()) {
						lowestValueIndex = j;
					}
				}
				// swap
				if (i != lowestValueIndex) {
					Collections.swap(codeList, i, lowestValueIndex);
				}
				
				// recursive
				//- 현제 위치(i)에서 정렬이 된 이후에(가장 낮은 값이 위치) 자기 자신(i)을 재귀 호출한다.
				//- 재귀호출이 먼저 이루어지고 정렬이 되면 위치가 바뀌면서 정렬이 안 되는 경우가 발생한다.
				sortCodeHierarchy(codeList.get(i));
			}
		}
	}
}
