package com.skplanet.iba.domain.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.support.utility.SecurityUtility;

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
	
	public List<AuthorityMenu> retrieveListByMenuIds(Integer... menuIds) {
		return authorityMenuMapper.selectListByMenuIds(menuIds);
	}
	
	@Transactional
	public Boolean add(AuthorityMenu authorityMenu) {
		// 등록자 id 설정
		authorityMenu.setRegUserId(SecurityUtility.getLoginUserId());
		
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
		// 권한 ID와 메뉴 ID 둘다 null이면 false(쿼리에서 조건절이 없게되어 테이블의 모든 데이터가 삭제된다.)
		if (authorityMenu.getAuthorityId() == null && authorityMenu.getMenuId() == null) {
			return false;
		}
		
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
	public Boolean removeByMenuId(Integer menuId) {
		AuthorityMenu authorityMenu = new AuthorityMenu();
		authorityMenu.setMenuId(menuId);
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
