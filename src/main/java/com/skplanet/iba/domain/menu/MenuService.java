package com.skplanet.iba.domain.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.authority.AuthorityMenuService;
import com.skplanet.iba.support.enumdata.UseState;
import com.skplanet.iba.support.utility.SecurityUtility;

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
		// 등록,수정자 id 설정
		menu.setRegUserId(SecurityUtility.getLoginUserId());
		menu.setModUserId(SecurityUtility.getLoginUserId());
		
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
		// 수정자 id 설정
		menu.setModUserId(SecurityUtility.getLoginUserId());
		
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
	
	public Menu createMenuHierarchy(List<Menu> menuList) {
		Menu dummyTop = new Menu();
		//dummyTop.setMenuId(-1);
		//dummyTop.setParentMenuId(-1);
		dummyTop.setMenuName("TOP");
		dummyTop.setDescription("최상위 메뉴");
		dummyTop.setUseState(UseState.USE);
		for (int i = menuList.size() - 1; i > -1; i--) {
			Menu temporary = menuList.get(i);
			if (temporary.getParentMenuId() != null && !"".equals(temporary.getParentMenuId())) {
				for (int j = menuList.size() - 2; j > -1; j--) {
					Menu target = menuList.get(j);
					if (target.getMenuId().equals(temporary.getParentMenuId())) {
						target.getChildMenu().add(temporary);
						break;
					}
				}
			} else {
				dummyTop.getChildMenu().add(temporary);
			}
			menuList.remove(i);
		}
		sortMenuHierarchy(dummyTop);
		return dummyTop;
	}
	
	private void sortMenuHierarchy(Menu menu) {
		if (menu.hasChildMenu()) {
			List<Menu> menuList = menu.getChildMenu();
			List<Menu> sortedMenuList = new ArrayList<>();
			for (int i = menuList.size() - 1; i > -1; i--) {
				sortMenuHierarchy(menuList.get(i));
				sortedMenuList.add(menuList.get(i));
			}
			menu.setChildMenu(sortedMenuList);
		}
	}
	
}
