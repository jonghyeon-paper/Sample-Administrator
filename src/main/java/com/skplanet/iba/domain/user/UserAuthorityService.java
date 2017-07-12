package com.skplanet.iba.domain.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthorityService {

	@Autowired
	private UserAuthorityMapper userAuthorityMapper;
	
	public UserAuthority retrieveAuthority(UserAuthority userAuthority) {
		return userAuthorityMapper.selectOne(userAuthority);
	}
	
	public List<UserAuthority> retrieveList(UserAuthority userAuthority) {
		return userAuthorityMapper.selectList(userAuthority);
	}
	
	@Transactional
	public Boolean addUserAuthority(List<UserAuthority> userAuthorityList) {
		// 등록,수정자 id 설정
		int insertCount = userAuthorityMapper.insert(userAuthorityList);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean addUserAuthority(UserAuthority userAuthority) {
		List<UserAuthority> userAuthorityList = new ArrayList<>();
		userAuthorityList.add(userAuthority);
		return this.addUserAuthority(userAuthorityList);
	}
	
	@Transactional
	public Boolean removeUserAuthority(UserAuthority userAuthority) {
		int deleteCount = userAuthorityMapper.delete(userAuthority);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeUserAuthorityByUserId(String userId) {
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setUserId(userId);
		return this.removeUserAuthority(userAuthority);
	}
	
	@Transactional
	public Boolean removeUserAuthorityByPrimaryKey(String userId) {
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setUserId(userId);
		return this.removeUserAuthority(userAuthority);
	}
}
