package com.skplanet.iba.domain.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		// 등록,수정자 id 설정
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
	public Boolean removeByPrimaryKey(String userId) {
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setUserId(userId);
		return this.remove(userAuthority);
	}
}
