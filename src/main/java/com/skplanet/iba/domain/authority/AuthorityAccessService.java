package com.skplanet.iba.domain.authority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.authority.enumdata.AccessMode;

@Service
public class AuthorityAccessService {

	@Autowired
	private AuthorityAccessMapper authorityAccessMapper;
	
	public AuthorityAccess retrieveAuthorityAccess(AuthorityAccess authorityAccess) {
		return authorityAccessMapper.selectOne(authorityAccess);
	}
	
	public List<AuthorityAccess> retrieveList(AuthorityAccess authorityAccess) {
		return authorityAccessMapper.selectList(authorityAccess);
	}
	
	@Transactional
	public Boolean addAuthorityAccess(List<AuthorityAccess> authorityAccessList) {
		// 등록,수정자 id 설정
		int insertCount = authorityAccessMapper.insert(authorityAccessList);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean addAuthorityAccess(AuthorityAccess authorityAccess) {
		List<AuthorityAccess> authorityAccessList = new ArrayList<>();
		authorityAccessList.add(authorityAccess);
		return this.addAuthorityAccess(authorityAccessList);
	}
	
	@Transactional
	public Boolean removeAuthorityAccess(AuthorityAccess authorityAccess) {
		int deleteCount = authorityAccessMapper.delete(authorityAccess);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeAuthorityAccessByAuthorityId(String authorityId) {
		AuthorityAccess authorityAccess = new AuthorityAccess();
		authorityAccess.setAuthorityId(authorityId);
		return this.removeAuthorityAccess(authorityAccess);
	}
	
	@Transactional
	public Boolean removeAuthorityAccessByPrimaryKey(String authorityId, AccessMode accessMode) {
		AuthorityAccess authorityAccess = new AuthorityAccess();
		authorityAccess.setAuthorityId(authorityId);
		authorityAccess.setAccessMode(accessMode);
		return this.removeAuthorityAccess(authorityAccess);
	}
}
