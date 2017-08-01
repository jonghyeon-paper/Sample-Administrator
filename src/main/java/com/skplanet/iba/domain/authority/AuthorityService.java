package com.skplanet.iba.domain.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.user.UserAuthorityService;
import com.skplanet.iba.support.utility.SecurityUtility;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Autowired
	private AuthorityAccessService authorityAccessService;
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	@Autowired
	private UserAuthorityService userAuthorityService;
	
	public Authority retrieve(Authority authority) {
		return authorityMapper.selectOne(authority);
	}
	
	public List<Authority> retrieveList(Authority authority) {
		return authorityMapper.selectList(authority);
	}
	
	@Transactional
	public Boolean add(Authority authority) {
		// 등록,수정자 id 설정
		authority.setRegUserId(SecurityUtility.getLoginUserId());
		authority.setModUserId(SecurityUtility.getLoginUserId());
		
		// 권한 정보 등록
		int insertCount = authorityMapper.insert(authority);
		
		// 추가 정보 등록
		if (insertCount > 0) {
			// 접근 권한 등록
			if (authority.getAuthorityAccessList() != null && !authority.getAuthorityAccessList().isEmpty()) {
				authorityAccessService.add(authority.getAuthorityAccessList());
			}
			
			// 권한 메뉴 등록
			if (authority.getAuthorityMenuList() != null && !authority.getAuthorityMenuList().isEmpty()) {
				authorityMenuService.add(authority.getAuthorityMenuList());
			}
		}
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean add(List<Authority> authorityList) {
		Boolean flag = true;
		for (Authority authority : authorityList) {
			flag = flag && this.add(authority);
		}
		return flag;
	}
	
	@Transactional
	public Boolean edit(Authority authority) {
		// 수정자 id 설정
		authority.setModUserId(SecurityUtility.getLoginUserId());
		
		// 권한 정보 수정
		int updateCount = authorityMapper.update(authority);
		
		// 추가 정보 수정
		if (updateCount > 0) {
			// 접근 권한 삭제, 등록
			authorityAccessService.removeByAuthorityId(authority.getAuthorityId());
			if (authority.getAuthorityAccessList() != null && !authority.getAuthorityAccessList().isEmpty()) {
				authorityAccessService.add(authority.getAuthorityAccessList());
			}
			
			// 권한 메뉴 삭제, 등록
			authorityMenuService.removeByAuthorityId(authority.getAuthorityId());
			if (authority.getAuthorityMenuList() != null && !authority.getAuthorityMenuList().isEmpty()) {
				authorityMenuService.add(authority.getAuthorityMenuList());
			}
		}
		return updateCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean remove(Authority authority) {
		// 접근 권한 삭제
		authorityAccessService.removeByAuthorityId(authority.getAuthorityId());
		
		// 권한 메뉴 삭제
		authorityMenuService.removeByAuthorityId(authority.getAuthorityId());
		
		// 사용자 권한 삭제
		userAuthorityService.removeByAuthorityId(authority.getAuthorityId());
		
		// 권한 삭제
		int deleteCount = authorityMapper.delete(authority);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeByAuthorityId(String authorityId) {
		Authority authority = new Authority();
		authority.setAuthorityId(authorityId);
		return this.remove(authority);
	}
	
	@Transactional
	public Boolean removeByPrimaryKey(String authorityId) {
		Authority authority = new Authority();
		authority.setAuthorityId(authorityId);
		return this.remove(authority);
	}
}
