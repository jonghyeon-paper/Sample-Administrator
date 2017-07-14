package com.skplanet.iba.domain.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IlmUserService {

	@Autowired
	private IlmUserMapper ilmUserMapper;
	
	public List<IlmUser> retrieveList(IlmUser ilmUser) {
		return ilmUserMapper.selectList(ilmUser);
	}
}
