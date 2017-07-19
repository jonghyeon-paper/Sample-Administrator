package com.skplanet.iba.domain.user;

import com.skplanet.iba.domain.common.BaseEntityMapper;

public interface UserMapper extends BaseEntityMapper<User> {

	// 상속받음
//	List<User> selectList(User user);
//	User selectOne(User user);
	int insert(User user);
//	int update(User user);
//	int delete(User user);
	
	/**
	 * 조건에 해당하는 사용자 목록페이지를 조회
	 * @param user
	 * @return
	 */
	//List<User> selectPageList(User user);
	
	/**
	 * 조건에 해당하는 사용자수를 조회
	 * @param user
	 * @return
	 */
	//int selectCount(User user);
}
