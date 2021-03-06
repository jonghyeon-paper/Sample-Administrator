package com.sample.administrator.model.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.administrator.core.archetype.AbstractPageRetrieveService;
import com.sample.administrator.model.user.entity.User;
import com.sample.administrator.model.user.persistence.UserMapper;

@Service
public class UserService extends AbstractPageRetrieveService<User, UserMapper> {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserAuthorityService userAuthorityService;
	
	public User retrieve(User user) {
		return userMapper.selectOne(user);
	}
	
	public User retrieve(String userId) {
		User user = new User();
		user.setUserId(userId);
		return this.retrieve(user);
	}
	
	public List<User> retrieveList(User user) {
		return userMapper.selectList(user);
	}
	
	@Transactional
	public Boolean add(User user) {
		// 사용자 등록
		int insertCount = userMapper.insert(user);
		
		// 추가 정보 등록
		if (insertCount > 0) {
			// 사용자 권한 등록
			if (user.getUserAuthorityList() != null && !user.getUserAuthorityList().isEmpty()) {
				userAuthorityService.add(user.getUserAuthorityList());
			}
		}
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean add(List<User> userList) {
		Boolean flag = true;
		for (User user : userList) {
			flag = flag && this.add(user);
		}
		return flag;
	}
	
	@Transactional
	public Boolean edit(User user) {
		// 사용자 정보 수정
		int updateCount = userMapper.update(user);
		
		// 추가 정보 수정
		if (updateCount > 0) {
			// 기존 권한 제거
			userAuthorityService.removeByUserId(user.getUserId());
			if (user.getUserAuthorityList() != null && !user.getUserAuthorityList().isEmpty()) {
				// 신규 권한 등록
				userAuthorityService.add(user.getUserAuthorityList());
			}
		}
		return updateCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean remove(User user) {
		// 사용자 권한 삭제
		userAuthorityService.removeByUserId(user.getUserId());
		
		// 사용자 정보 삭제
		int deleteCount = userMapper.delete(user);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeByUserId(String userId) {
		User user = new User();
		user.setUserId(userId);
		return this.remove(user);
	}
	
	@Transactional
	public Boolean removeByPrimaryKey(String userId) {
		User user = new User();
		user.setUserId(userId);
		return this.remove(user);
	}
}
