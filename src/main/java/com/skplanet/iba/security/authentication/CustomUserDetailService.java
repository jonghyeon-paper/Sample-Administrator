package com.skplanet.iba.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
	
//	@Autowired
//	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/*
		String userId = username;
		Users user = userMapper.selectOne(userId);
		if (user == null) {
			throw new UsernameNotFoundException(userId);
		}
		
		List<String> roleList = userMapper.selectRoleList(userId);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (roleList != null && !roleList.isEmpty()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NOTHING"));
		} else {
			for (String role : roleList) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.toUpperCase()));
			}
		}
		
		return new User(user.getUserId(), user.getPassword(), grantedAuthorities);
		*/
		
		// fixed user(nothing DB)
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NOTHING"));
		return new User(username, "", grantedAuthorities);
	}

}
