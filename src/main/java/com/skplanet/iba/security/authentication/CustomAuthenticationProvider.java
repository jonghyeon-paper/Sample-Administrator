package com.skplanet.iba.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.skplanet.iba.security.authentication.customization.CustomBadCredentialsException;

/**
 * LDAP 인증 실패일 경우 실행됨
 * - 'superuser'만 사용가능
 * - 패스워드 인증 없음(DB에 패스워드 값이 없음)
 * @author good
 *
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		LOGGER.info("name >> " + auth.getName());
		LOGGER.info("principal >> " + auth.getPrincipal().toString());
		LOGGER.info("credential >> " + auth.getCredentials().toString());
		
		if (!"superuser".equals(auth.getPrincipal().toString())) {
			LOGGER.warn("LOCAL - Bad Credentials");
			throw new CustomBadCredentialsException("LOCAL - Bad Credentials", auth.getPrincipal().toString());
		}
		
		// step1. 사용자 정보 조회
		// id 사용(Backoffice Database)
		UserDetails userDetail = customUserDetailService.loadUserByUsername(auth.getPrincipal().toString());
		
		// principal에 사용자 이름이 아닌 사용자 정보를 모두 넣는다.
		return new UsernamePasswordAuthenticationToken(userDetail, auth.getCredentials(), userDetail.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}
