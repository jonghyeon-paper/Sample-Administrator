package com.sample.administrator.model.menu;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.administrator.model.authority.AuthorityMenuService;
import com.sample.administrator.model.menu.entity.Menu;
import com.sample.administrator.model.menu.entity.MenuDependence;
import com.sample.administrator.model.menu.persistence.MenuMapper;
import com.sample.administrator.web.element.UseState;

@Service
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private MenuDependenceService menuDependenceService;
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	public Menu retrieveMenu(Menu menu) {
		return menuMapper.selectOne(menu);
	}
	
	public List<Menu> retrieveList(Menu menu) {
		return menuMapper.selectList(menu);
	}
	
	public List<Menu> retrieveListByMenuIds(Integer... menuIds) {
		return menuMapper.selectListByMenuIds(menuIds);
	}
	
	@Transactional
	public Boolean add(Menu menu) {
		// 메뉴 등록
		int insertCount = menuMapper.insert(menu);
		
		if (insertCount > 0) {
			// 의존 URI 등록
			if (menu.getMenuDependenceList() != null && !menu.getMenuDependenceList().isEmpty()) {
				// 메뉴 ID 설정
				for (MenuDependence menuDependence : menu.getMenuDependenceList()) {
					menuDependence.setMenuId(menu.getMenuId());
				}
				menuDependenceService.add(menu.getMenuDependenceList());
			}
		}
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean add(List<Menu> menuList) {
		Boolean flag = true;
		for (Menu menu : menuList) {
			flag = flag && this.add(menu);
		}
		return flag;
	}
	
	@Transactional
	public Boolean edit(Menu menu) {
		// 메뉴 수정
		int updateCount = menuMapper.update(menu);
		
		if (updateCount > 0) {
			// 의존 URI 삭제, 등록
			menuDependenceService.removeByMenuId(menu.getMenuId());
			if (menu.getMenuDependenceList() != null && !menu.getMenuDependenceList().isEmpty()) {
				// 메뉴 ID 설정
				for (MenuDependence menuDependence : menu.getMenuDependenceList()) {
					menuDependence.setMenuId(menu.getMenuId());
				}
				menuDependenceService.add(menu.getMenuDependenceList());
			}
		}
		
		return updateCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean remove(Menu menu) {
		// 의존 URI 삭제
		menuDependenceService.removeByMenuId(menu.getMenuId());
		
		// 권한 메뉴 삭제
		authorityMenuService.removeByMenuId(menu.getMenuId());
		
		// 메뉴 삭제
		int deleteCount = menuMapper.delete(menu);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeByMenuId(Integer menuId) {
		Menu menu = new Menu();
		menu.setMenuId(menuId);
		return this.remove(menu);
	}
	
	@Transactional
	public Boolean removeByPrimaryKey(Integer menuId) {
		Menu menu = new Menu();
		menu.setMenuId(menuId);
		return this.remove(menu);
	}
	
	public Menu getMenuHierarchy(Menu menu) {
		List<Menu> menuList = this.retrieveList(menu);
		return createMenuHierarchy(menuList);
	}
	
	/**
	 * 계층구조 오브젝트 생성
	 * @param menuList
	 * @return
	 */
	public Menu createMenuHierarchy(List<Menu> menuList) {
		Menu dummyTop = new Menu();
		//dummyTop.setMenuId(-1);
		//dummyTop.setParentMenuId(-1);
		dummyTop.setMenuName("TOP");
		dummyTop.setDescription("최상위 메뉴");
		dummyTop.setUseState(UseState.USE);
		
		// 계층 구조를 찾아가기 위한 맵
		//- 위에서 아래로 찾아가는 top > down구조로 정의됨.
		//- queue데이터로 top > down으로 데이터를 넣고, top > down으로 데이터를 꺼낸다.
		Map<Integer, Queue<Integer>> hierarchyMap = new HashMap<>();
		
		// 최상위 메뉴 등록
		//- 더미 객체에 최상위 레벨의 메뉴를 자식으로 추가한다.
		for (int i = 0; i < menuList.size() ; i++) {
			Menu item = menuList.get(i);
			if (item.getParentMenuId() == null) {
				// 하위에 등록한다.
				dummyTop.addChildMenu(item);
				
				// 계층 구조를 맵에 추가한다.
				//- 1레벨
				Queue<Integer> temp = new LinkedList<>();
				temp.add(item.getMenuId());
				hierarchyMap.put(item.getMenuId(), temp);
				
				// 사용 된 데이터는 list에서 제거한다.
				menuList.remove(i);
				i--;
			}
		}
		
		// 하위 메뉴 등록
		//- list의 값이 존재하면 반복해서 실행된다.
		//- 마지막 루프 사이즈값이 목록의 사이즈보다 크면 반복 실행한다.(부모가 없는 경우 무한루프)
		int lastLoopSize = menuList.size() + 1;
		while (menuList.size() > 0 && menuList.size() < lastLoopSize) {
			lastLoopSize = menuList.size();
			
			for (int i = 0; i < menuList.size() ; i++) {
				Menu item = menuList.get(i);
				
				// 계층 구조맵에 부모값이 있으면 찾아간다.
				//- 자기 자신의 계층을 계층 구조맵에 등록.
				//- 부모를 찾아가서 하위에 등록.
				//- list에서 자신을 제거.
				if (hierarchyMap.containsKey(item.getParentMenuId())) {
					Integer parentId = item.getParentMenuId();
					
					// 계층 구조를 맵에 추가한다.
					//- n레벨
					Queue<Integer> newHierarchyMap = new LinkedList<>(hierarchyMap.get(parentId)); // 새 인스턴스
					newHierarchyMap.add(item.getMenuId());
					hierarchyMap.put(item.getMenuId(), newHierarchyMap);
					
					// 하위에 등록한다.
					//- n레벨의 부모를 찾아가기 위해 재귀호출한다.
					Queue<Integer> parentHierarchyMap = new LinkedList<>(hierarchyMap.get(parentId)); // 새 인스턴스
					findObjectAndAdd(item, parentHierarchyMap, dummyTop.getChildMenu());
					
					//사용 된 데이터는 list에서 제거한다.
					menuList.remove(i);
					i--;
				}
			}
		}
		
		
		sortMenuHierarchy(dummyTop);
		return dummyTop;
	}
	
	/**
	 * 계층 구조를 찾는다
	 * @param targetItem - 대상 오브젝트
	 * @param hierarchyMap - 계층 구조맵
	 * @param list - 대상 목록
	 */
	private void findObjectAndAdd(Menu targetItem, Queue<Integer> hierarchyMap, List<Menu> list) {
		// 계층 구조맵에서 맨 위의 값을 꺼낸다.
		Integer findMenuId = hierarchyMap.poll();
		Menu parentMenu = null;
		
		// 대상 목록에서 일치하는 대상을 찾는다.
		for (Menu menu : list) {
			if (menu.getMenuId().equals(findMenuId)) {
				parentMenu = menu;
			}
		}
		
		if (parentMenu != null) {
			if (hierarchyMap.size() == 0) {
				// 계층 구조맵이 없다면 대상 오브젝트를 하위에 추가한다.
				parentMenu.addChildMenu(targetItem);
			} else {
				// 계층 구조맵이 있다면 재귀호출.
				findObjectAndAdd(targetItem, hierarchyMap, parentMenu.getChildMenu());
			}
		}
	}
	
	/**
	 * 순서값을 사용한 정렬
	 * - 선택 정렬 알고리즘.
	 * - 순서값이 없는 데이터는 아무동작 안함.
	 * @param menu
	 */
	private void sortMenuHierarchy(Menu menu) {	
		if (menu.hasChildMenu()) {
			List<Menu> menuList = menu.getChildMenu();
			for (int i = 0; i < menuList.size(); i++) {
				if (menuList.get(i).getDisplayOrder() == null) {
					continue;
				}
				int lowestValueIndex = i;
				for (int j = i + 1; j < menuList.size(); j++) {
					if (menuList.get(j).getDisplayOrder() == null) {
						continue;
					}
					if (menuList.get(j).getDisplayOrder() < menuList.get(lowestValueIndex).getDisplayOrder()) {
						lowestValueIndex = j;
					}
				}
				// swap
				if (i != lowestValueIndex) {
					Collections.swap(menuList, i, lowestValueIndex);
				}
				
				// recursive
				//- 현제 위치(i)에서 정렬이 된 이후에(가장 낮은 값이 위치) 자기 자신(i)을 재귀 호출한다.
				//- 재귀호출이 먼저 이루어지고 정렬이 되면 위치가 바뀌면서 정렬이 안 되는 경우가 발생한다.
				sortMenuHierarchy(menuList.get(i));
			}
		}
	}
	
}
