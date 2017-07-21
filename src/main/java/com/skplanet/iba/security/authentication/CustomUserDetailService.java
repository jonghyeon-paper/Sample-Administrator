package com.skplanet.iba.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.skplanet.iba.domain.user.User;
import com.skplanet.iba.domain.user.UserAuthority;
import com.skplanet.iba.domain.user.UserAuthorityService;
import com.skplanet.iba.domain.user.UserService;
import com.skplanet.iba.security.authentication.customization.CustomUserDetail;

public class CustomUserDetailService implements UserDetailsService {
	
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
		User user = userService.retrieve(userId);
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
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPTY"));
		}
		
		return new CustomUserDetail(user.getUserId(), "", grantedAuthorities, user);
	}

}
