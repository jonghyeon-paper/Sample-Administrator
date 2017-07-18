package com.skplanet.iba.domain.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuDependenceService {

	@Autowired
	private MenuDependenceMapper menuDependenceMapper;
	
	public MenuDependence retrieve(MenuDependence menuDependence) {
		return menuDependenceMapper.selectOne(menuDependence);
	}
	
	public List<MenuDependence> retrieveList(MenuDependence menuDependence) {
		return menuDependenceMapper.selectList(menuDependence);
	}
	
	@Transactional
	public Boolean add(MenuDependence menuDependence) {
		// 등록,수정자 id 설정
		int insertCount = menuDependenceMapper.insert(menuDependence);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean add(List<MenuDependence> menuDependenceList) {
		Boolean flag = true;
		for (MenuDependence menuDependence : menuDependenceList) {
			flag = flag && this.add(menuDependence);
		}
		return flag;
	}
	
	@Transactional
	public Boolean edit(MenuDependence menuDependence) {
		// 수정자 id 설정
		int updateCount = menuDependenceMapper.update(menuDependence);
		return updateCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean remove(MenuDependence menuDependence) {
		int deleteCount = menuDependenceMapper.delete(menuDependence);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeByMenuId(Integer menuId) {
		MenuDependence menuDependence = new MenuDependence();
		menuDependence.setMenuId(menuId);
		return this.remove(menuDependence);
	}
	
	@Transactional
	public Boolean removeByPrimaryKey(Integer sequence) {
		MenuDependence menuDependence = new MenuDependence();
		menuDependence.setSequence(sequence);
		return this.remove(menuDependence);
	}
}
