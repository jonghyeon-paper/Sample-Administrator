package com.skplanet.iba.domain.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityMenuService {

	@Autowired
	private AuthorityMenuMapper authorityMenuMapper;
	
	public AuthorityMenu retrieve(AuthorityMenu authorityMenu) {
		return authorityMenuMapper.selectOne(authorityMenu);
	}
	
	public List<AuthorityMenu> retrieveList(AuthorityMenu authorityMenu) {
		return authorityMenuMapper.selectList(authorityMenu);
	}
	
	public List<AuthorityMenu> retrieveListByAuthorityIds(String... authorityIds) {
		return authorityMenuMapper.selectListByAuthorityIds(authorityIds);
	}
	
	@Transactional
	public Boolean add(AuthorityMenu authorityMenu) {
		// 등록,수정자 id 설정
		int insertCount = authorityMenuMapper.insert(authorityMenu);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean add(List<AuthorityMenu> authorityMenuList) {
		Boolean flag = true;
		for (AuthorityMenu authorityMenu : authorityMenuList) {
			flag = flag && this.add(authorityMenu);
		}
		return flag;
	}
	
	@Transactional
	public Boolean remove(AuthorityMenu authorityMenu) {
		int deleteCount = authorityMenuMapper.delete(authorityMenu);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeByAuthorityId(String authorityId) {
		AuthorityMenu authorityMenu = new AuthorityMenu();
		authorityMenu.setAuthorityId(authorityId);
		return this.remove(authorityMenu);
	}
	
	@Transactional
	public Boolean removeByPrimaryKey(String authorityId, Integer menuId) {
		AuthorityMenu authorityMenu = new AuthorityMenu();
		authorityMenu.setAuthorityId(authorityId);
		authorityMenu.setMenuId(menuId);
		return this.remove(authorityMenu);
	}
}
