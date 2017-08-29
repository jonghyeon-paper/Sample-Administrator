package com.sample.administrator.model.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.administrator.model.user.entity.UserAuthority;
import com.sample.administrator.model.user.persistence.UserAuthorityMapper;

@Service
public class UserAuthorityService {

	@Autowired
	private UserAuthorityMapper userAuthorityMapper;
	
	public UserAuthority retrieve(UserAuthority userAuthority) {
		return userAuthorityMapper.selectOne(userAuthority);
	}
	
	public List<UserAuthority> retrieveList(UserAuthority userAuthority) {
		return userAuthorityMapper.selectList(userAuthority);
	}
	
	@Transactional
	public Boolean add(UserAuthority userAuthority) {
		int insertCount = userAuthorityMapper.insert(userAuthority);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean add(List<UserAuthority> userAuthorityList) {
		Boolean flag = true;
		for (UserAuthority userAuthority : userAuthorityList) {
			flag = flag && this.add(userAuthority);
		}
		return flag;
	}
	
	@Transactional
	public Boolean remove(UserAuthority userAuthority) {
		// 권한 ID와 사용자 ID 둘다 null이면 false(쿼리에서 조건절이 없게되어 테이블의 모든 데이터가 삭제된다.)
		if (userAuthority.getUserId() == null && userAuthority.getAuthorityId() == null) {
			return false;
		}
		
		int deleteCount = userAuthorityMapper.delete(userAuthority);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeByUserId(String userId) {
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setUserId(userId);
		return this.remove(userAuthority);
	}
	
	@Transactional
	public Boolean removeByAuthorityId(String authorityId) {
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setAuthorityId(authorityId);
		return this.remove(userAuthority);
	}
	
	@Transactional
	public Boolean removeByPrimaryKey(String userId) {
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setUserId(userId);
		return this.remove(userAuthority);
	}
}
