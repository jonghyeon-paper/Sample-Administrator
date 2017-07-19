package com.skplanet.iba.domain.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.support.enumdata.UseState;

@Service
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private MenuDependenceService menuDependenceService;
	
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
	public Boolean addMenu(Menu menu) {
		// 등록,수정자 id 설정
		
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
	public Boolean addMenu(List<Menu> menuList) {
		Boolean flag = true;
		for (Menu menu : menuList) {
			flag = flag && this.addMenu(menu);
		}
		return flag;
	}
	
	@Transactional
	public Boolean editMenu(Menu menu) {
		// 수정자 id 설정
		
		// 메뉴 수정
		int updateCount = menuMapper.update(menu);
		
		if (updateCount > 0) {
			// 의존 URI 삭제, 등록
			if (menu.getMenuDependenceList() != null && !menu.getMenuDependenceList().isEmpty()) {
				menuDependenceService.removeByMenuId(menu.getMenuId());
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
	public Boolean removeMenu(Menu menu) {
		int deleteCount = menuMapper.delete(menu);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeMenuByMenuId(Integer menuId) {
		Menu menu = new Menu();
		menu.setMenuId(menuId);
		return this.removeMenu(menu);
	}
	
	@Transactional
	public Boolean removeMenuByPrimaryKey(Integer menuId) {
		Menu menu = new Menu();
		menu.setMenuId(menuId);
		return this.removeMenu(menu);
	}
	
	public Menu getMenuHierarchy() {
		Menu conditon = new Menu();
		//conditon.setUseState(UseState.USE);
		List<Menu> menuList = this.retrieveList(conditon);
		return createMenuHierarchy(menuList);
	}
	
	public Menu createMenuHierarchy(List<Menu> menuList) {
		Menu dummyTop = new Menu();
		dummyTop.setMenuName("TOP");
		dummyTop.setMenuId(-1);
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
