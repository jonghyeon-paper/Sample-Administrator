package com.skplanet.iba.domain.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginHistoryService {

	@Autowired
	private LoginHistoryMapper loginHistoryMapper;
	
	public Boolean add(LoginHistory loginHistory) {
		int insertCount = loginHistoryMapper.insert(loginHistory);
		return insertCount > 0 ? true :false;
	}
}
