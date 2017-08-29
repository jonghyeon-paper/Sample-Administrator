package com.sample.administrator.security.authentication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sample.administrator.model.user.UserAuthorityService;
import com.sample.administrator.model.user.UserService;
import com.sample.administrator.model.user.entity.User;
import com.sample.administrator.model.user.entity.UserAuthority;
import com.sample.administrator.web.element.UseState;

public class CustomUserDetailService implements UserDetailsService {
	
	final static String ROLE_EMPTY = "ROLE_EMPTY";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthorityService UserAuthorityService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// temporary data
		/*
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPTY"));
		return new org.springframework.security.core.userdetails.User(username, "", grantedAuthorities);
		 */
		
		String userId = username;
		User userSearchCondition = new User();
		userSearchCondition.setUserId(userId);
		userSearchCondition.setUseState(UseState.USE);
		User user = userService.retrieve(userSearchCondition);
		if (user == null) {
			throw new UsernameNotFoundException(userId);
		}
		
		UserAuthority userAuthoritySearchCondition = new UserAuthority();
		userAuthoritySearchCondition.setUserId(userId);
		List<UserAuthority> targetUserAuthorityList = UserAuthorityService.retrieveList(userAuthoritySearchCondition);
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (targetUserAuthorityList != null && !targetUserAuthorityList.isEmpty()) {
			for (UserAuthority item : targetUserAuthorityList) {
				grantedAuthorities.add(new SimpleGrantedAuthority(item.getAuthorityId()));
			}
		} else {
			// ROLE_EMPTY는 사용자의 권한이 하나도 없을 때 주는 권한이다. 사용되는 곳은 없다.
			grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_EMPTY));
		}
		
		return new CustomUserDetail(user.getUserId(), "", grantedAuthorities, user);
	}

}
