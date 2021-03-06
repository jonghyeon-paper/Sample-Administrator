package com.sample.administrator.model.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.administrator.model.authority.entity.AuthorityAccess;
import com.sample.administrator.model.authority.entity.element.AccessMode;
import com.sample.administrator.model.authority.persistence.AuthorityAccessMapper;

@Service
public class AuthorityAccessService {

	@Autowired
	private AuthorityAccessMapper authorityAccessMapper;
	
	public AuthorityAccess retrieve(AuthorityAccess authorityAccess) {
		return authorityAccessMapper.selectOne(authorityAccess);
	}
	
	public List<AuthorityAccess> retrieveList(AuthorityAccess authorityAccess) {
		return authorityAccessMapper.selectList(authorityAccess);
	}
	
	public List<AuthorityAccess> retrieveListByAuthorityIds(String... authorityIds) {
		return authorityAccessMapper.selectListByAuthorityIds(authorityIds);
	}
	
	@Transactional
	public Boolean add(AuthorityAccess authorityAccess) {
		int insertCount = authorityAccessMapper.insert(authorityAccess);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean add(List<AuthorityAccess> authorityAccessList) {
		Boolean flag = true;
		for (AuthorityAccess authorityAccess : authorityAccessList) {
			flag = flag && this.add(authorityAccess);
		}
		return flag;
	}
	
	
	@Transactional
	public Boolean remove(AuthorityAccess authorityAccess) {
		int deleteCount = authorityAccessMapper.delete(authorityAccess);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeByAuthorityId(String authorityId) {
		AuthorityAccess authorityAccess = new AuthorityAccess();
		authorityAccess.setAuthorityId(authorityId);
		return this.remove(authorityAccess);
	}
	
	@Transactional
	public Boolean removeByPrimaryKey(String authorityId, AccessMode accessMode) {
		AuthorityAccess authorityAccess = new AuthorityAccess();
		authorityAccess.setAuthorityId(authorityId);
		authorityAccess.setAccessMode(accessMode);
		return this.remove(authorityAccess);
	}
}
