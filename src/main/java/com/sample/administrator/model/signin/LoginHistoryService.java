package com.sample.administrator.model.signin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.administrator.model.signin.entity.LoginHistory;
import com.sample.administrator.model.signin.persistence.LoginHistoryMapper;

@Service
public class LoginHistoryService {

	@Autowired
	private LoginHistoryMapper loginHistoryMapper;
	
	public Boolean add(LoginHistory loginHistory) {
		int insertCount = loginHistoryMapper.insert(loginHistory);
		return insertCount > 0 ? true :false;
	}
}
