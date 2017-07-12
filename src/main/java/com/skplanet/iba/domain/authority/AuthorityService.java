package com.skplanet.iba.domain.authority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;
	
	public Authority retrieveAuthority(Authority authority) {
		return authorityMapper.selectOne(authority);
	}
	
	public List<Authority> retrieveList(Authority authority) {
		return authorityMapper.selectList(authority);
	}
	
	@Transactional
	public Boolean addAuthority(List<Authority> authorityList) {
		// 등록,수정자 id 설정
		int insertCount = authorityMapper.insert(authorityList);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean addAuthority(Authority authority) {
		List<Authority> authorityList = new ArrayList<>();
		authorityList.add(authority);
		return this.addAuthority(authorityList);
	}
	
	@Transactional
	public Boolean editAuthority(Authority authority) {
		// 수정자 id 설정
		int updateCount = authorityMapper.update(authority);
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
