package com.skplanet.iba.domain.user;

import java.util.List;

public interface UserMapper {

	List<User> selectList(User user);
	User selectOne(User user);
	int insert(List<User> userList);
	int update(User user);
	int delete(User user);
}
