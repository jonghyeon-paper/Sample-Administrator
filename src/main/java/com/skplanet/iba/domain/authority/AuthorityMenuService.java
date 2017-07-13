package com.skplanet.iba.domain.authority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityMenuService {

	@Autowired
	private AuthorityMenuMapper authorityMenuMapper;
	
	public AuthorityMenu retrieveAuthority(AuthorityMenu authorityMenu) {
		return authorityMenuMapper.selectOne(authorityMenu);
	}
	
	public List<AuthorityMenu> retrieveList(AuthorityMenu authorityMenu) {
		return authorityMenuMapper.selectList(authorityMenu);
	}
	
	public List<AuthorityMenu> retrieveListByAuthorityIds(String... authorityIds) {
		return authorityMenuMapper.selectListByAuthorityIds(authorityIds);
	}
	
	@Transactional
	public Boolean addAuthorityMenu(List<AuthorityMenu> authorityMenuList) {
		// 등록,수정자 id 설정
		int insertCount = authorityMenuMapper.insert(authorityMenuList);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean addAuthorityMenu(AuthorityMenu authorityMenu) {
		List<AuthorityMenu> authorityMenuList = new ArrayList<>();
		authorityMenuList.add(authorityMenu);
		return this.addAuthorityMenu(authorityMenuList);
	}
	
	@Transactional
	public Boolean removeAuthorityMenu(AuthorityMenu authorityMenu) {
		int deleteCount = authorityMenuMapper.delete(authorityMenu);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeAuthorityMenuByAuthorityId(String authorityId) {
		AuthorityMenu authorityMenu = new AuthorityMenu();
		authorityMenu.setAuthorityId(authorityId);
		return this.removeAuthorityMenu(authorityMenu);
	}
	
	@Transactional
	public Boolean removeAuthorityMenuByPrimaryKey(String authorityId, Integer menuId) {
		AuthorityMenu authorityMenu = new AuthorityMenu();
		authorityMenu.setAuthorityId(authorityId);
		authorityMenu.setMenuId(menuId);
		return this.removeAuthorityMenu(authorityMenu);
	}
}
