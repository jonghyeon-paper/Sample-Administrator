package com.skplanet.iba.domain.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Autowired
	private AuthorityAccessService authorityAccessService;
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	public Authority retrieveAuthority(Authority authority) {
		return authorityMapper.selectOne(authority);
	}
	
	public List<Authority> retrieveList(Authority authority) {
		return authorityMapper.selectList(authority);
	}
	
	@Transactional
	public Boolean addAuthority(Authority authority) {
		// 등록,수정자 id 설정
		
		// 권한 정보 등록
		int insertCount = authorityMapper.insert(authority);
		
		// 추가 정보 등록
		if (insertCount > 0) {
			// 접근 권한 등록
			if (authority.getAuthorityAccessList() != null && !authority.getAuthorityAccessList().isEmpty()) {
				authorityAccessService.addAuthorityAccess(authority.getAuthorityAccessList());
			}
			
			// 권한 메뉴 등록
			if (authority.getAuthorityMenuList() != null && !authority.getAuthorityMenuList().isEmpty()) {
				authorityMenuService.addAuthorityMenu(authority.getAuthorityMenuList());
			}
		}
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean addAuthority(List<Authority> authorityList) {
		Boolean flag = false;
		for (Authority authority : authorityList) {
			flag = flag && this.addAuthority(authority);
		}
		return flag;
	}
	
	@Transactional
	public Boolean editAuthority(Authority authority) {
		// 수정자 id 설정
		
		// 권한 정보 수정
		int updateCount = authorityMapper.update(authority);
		
		// 추가 정보 수정
		if (updateCount > 0) {
			// 접근 권한 삭제, 등록
			if (authority.getAuthorityAccessList() != null && !authority.getAuthorityAccessList().isEmpty()) {
				authorityAccessService.removeAuthorityAccessByAuthorityId(authority.getAuthorityId());
				authorityAccessService.addAuthorityAccess(authority.getAuthorityAccessList());
			}
			
			// 권한 메뉴 삭제, 등록
			if (authority.getAuthorityMenuList() != null && !authority.getAuthorityMenuList().isEmpty()) {
				authorityMenuService.removeAuthorityMenuByAuthorityId(authority.getAuthorityId());
				authorityMenuService.addAuthorityMenu(authority.getAuthorityMenuList());
			}
		}
		return updateCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeAuthority(Authority authority) {
		int deleteCount = authorityMapper.delete(authority);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeAuthorityByAuthorityId(String authorityId) {
		Authority authority = new Authority();
		authority.setAuthorityId(authorityId);
		return this.removeAuthority(authority);
	}
	
	@Transactional
	public Boolean removeAuthorityByPrimaryKey(String authorityId) {
		Authority authority = new Authority();
		authority.setAuthorityId(authorityId);
		return this.removeAuthority(authority);
	}
}
