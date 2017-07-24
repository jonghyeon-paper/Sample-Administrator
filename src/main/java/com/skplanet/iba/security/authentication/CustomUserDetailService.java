package com.skplanet.iba.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.skplanet.iba.domain.authority.AuthorityAccess;
import com.skplanet.iba.domain.authority.AuthorityAccessService;
import com.skplanet.iba.domain.user.User;
import com.skplanet.iba.domain.user.UserAuthority;
import com.skplanet.iba.domain.user.UserAuthorityService;
import com.skplanet.iba.domain.user.UserService;
import com.skplanet.iba.security.authentication.customization.CustomUserDetail;

public class CustomUserDetailService implements UserDetailsService {
	
	final static String ROLE_EMPTY = "ROLE_EMPTY";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthorityService UserAuthorityService;
	
	@Autowired
	private AuthorityAccessService authorityAccessService;

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
				List<AuthorityAccess> targetAuthorityAccessList = authorityAccessService.retrieveListByAuthorityIds(item.getAuthorityId());
				if (targetAuthorityAccessList != null && !targetAuthorityAccessList.isEmpty()) {
					// 접근 권한이 있으면 '권한_접근모드'로 권한명을 설정한다.
					for (AuthorityAccess authorityAccess : targetAuthorityAccessList) {
						grantedAuthorities.add(new SimpleGrantedAuthority(item.getAuthorityId() + "_" + authorityAccess.getAccessMode()));
					}
				} else {
					// 접근 권한이 없으면 권한을 권한명으로 설정한다.
					grantedAuthorities.add(new SimpleGrantedAuthority(item.getAuthorityId()));
				}
			}
		} else {
			// ROLE_EMPTY는 사용자의 권한이 하나도 없을 때 주는 권한이다. 사용되는 곳은 없다.
			grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_EMPTY));
		}
		
		return new CustomUserDetail(user.getUserId(), "", grantedAuthorities, user);
	}

}
