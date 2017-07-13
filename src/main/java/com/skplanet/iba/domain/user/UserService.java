package com.skplanet.iba.domain.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.framework.data.PagingContents;
import com.skplanet.iba.framework.data.PagingRequest;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User retrieveUser(User user) {
		return userMapper.selectOne(user);
	}
	
	public User retrieveUser(String userId) {
		User user = new User();
		user.setUserId(userId);
		return this.retrieveUser(user);
	}
	
	public List<User> retrieveList(User user) {
		return userMapper.selectList(user);
	}
	
	@Transactional
	public Boolean addUser(List<User> userList) {
		// 등록,수정자 id 설정
		int insertCount = userMapper.insert(userList);
		return insertCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean addUser(User user) {
		List<User> userList = new ArrayList<>();
		userList.add(user);
		return this.addUser(userList);
	}
	
	@Transactional
	public Boolean editUser(User user) {
		// 수정자 id 설정
		int updateCount = userMapper.update(user);
		return updateCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeUser(User user) {
		int deleteCount = userMapper.delete(user);
		return deleteCount > 0 ? true : false;
	}
	
	@Transactional
	public Boolean removeUserByUserId(String userId) {
		User user = new User();
		user.setUserId(userId);
		return this.removeUser(user);
	}
	
	@Transactional
	public Boolean removeUserByPrimaryKey(String userId) {
		User user = new User();
		user.setUserId(userId);
		return this.removeUser(user);
	}
	
	/**
	 * 페이지 처리된 user목록 객체를 조회
	 * @param user
	 * @param pagingRequest
	 * @return
	 */
	public PagingContents<User> retrievePageList(User user, PagingRequest pagingRequest) {
		user.setStart(pagingRequest.getStartIndex());
		user.setOffset(pagingRequest.getCountPerPage());
		List<User> userList = userMapper.selectPageList(user);
		Integer userCount = userMapper.selectCount(user);
		return new PagingContents<User>(pagingRequest.getPage(), pagingRequest.getCountPerPage(), userList, userCount);
	}
}
