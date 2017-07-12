package com.skplanet.iba.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		LOGGER.info("name >> " + auth.getName());
		LOGGER.info("principal >> " + auth.getPrincipal().toString());
		LOGGER.info("credential >> " + auth.getCredentials().toString());
		
		// setp1. ILM 인증
		// ID, password 사용(ILM server)
		if (ilmSystemUserExist(auth.getPrincipal().toString(), auth.getCredentials().toString())) {
			
			// step2. 사용자 정보 조회
			// id 사용(Backoffice Database)
			UserDetails userDetail = customUserDetailService.loadUserByUsername(auth.getPrincipal().toString());
			
			// principal에 사용자 정보를 모두 넣는다.
			return new UsernamePasswordAuthenticationToken(userDetail, auth.getCredentials(), userDetail.getAuthorities());
		} else {
			LOGGER.warn("Bad Credentials");
			throw new BadCredentialsException("Bad Credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
	/**
	 * ILM 시스템 로그인 
	 * @param id
	 * @param password
	 * @return
	 */
	private boolean ilmSystemUserExist(String id, String password) {
		// 프로세스 작성
		return true;
	}

}
