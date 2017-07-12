package com.skplanet.iba.domain.authority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.authority.enumdata.AccessMode;

@Service
public class AuthorityAccessModeService {

	@Autowired
	private AuthorityAccessModeMapper authorityAccessModeMapper;
	
	public AuthorityAccessMode retrieveAuthorityAccessMode(AuthorityAccessMode authorityAccessMode) {
		return authorityAccessModeMapper.selectOne(authorityAccessMode);
	}
	
	public List<AuthorityAccessMode> retrieveList(AuthorityAccessMode authorityAccessMode) {
		return authorityAccessModeMapper.selectList(authorityAccessMode);
	}
	
	@Transactional
	public Boolean addAuthorityAccessMode(List<AuthorityAccessMode> authorityAccessModeList) {
		// 등록,수정자 id 설정
		int insertCount = authorityAccessModeMapper.insert(authorityAccessModeList);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean addAuthorityAccessMode(AuthorityAccessMode authorityAccessMode) {
		List<AuthorityAccessMode> authorityAccessModeList = new ArrayList<>();
		authorityAccessModeList.add(authorityAccessMode);
		return this.addAuthorityAccessMode(authorityAccessModeList);
	}
	
	@Transactional
	public Boolean removeAuthorityAccessMode(AuthorityAccessMode authorityAccessMode) {
		int deleteCount = authorityAccessModeMapper.delete(authorityAccessMode);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeAuthorityAccessModeByAuthorityId(String authorityId) {
		AuthorityAccessMode authorityAccessMode = new AuthorityAccessMode();
		authorityAccessMode.setAuthorityId(authorityId);
		return this.removeAuthorityAccessMode(authorityAccessMode);
	}
	
	@Transactional
	public Boolean removeAuthorityAccessModeByPrimaryKey(String authorityId, AccessMode accessMode) {
		AuthorityAccessMode authorityAccessMode = new AuthorityAccessMode();
		authorityAccessMode.setAuthorityId(authorityId);
		authorityAccessMode.setAccessMode(accessMode);
		return this.removeAuthorityAccessMode(authorityAccessMode);
	}
}
