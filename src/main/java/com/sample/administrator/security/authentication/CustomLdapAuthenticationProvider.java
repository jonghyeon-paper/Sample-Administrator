package com.sample.administrator.security.authentication;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.sample.administrator.security.authentication.service.CustomUserDetailService;

public class CustomLdapAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomLdapAuthenticationProvider.class);
	
	/* ldap 설치된 서버 ip주소 */
	private static final String LDAP_SERVER = "ldap://xxx.xxx.xxx.xxx:xxxx"; 
	/* (userId)+계정정보 */
	private static final String LDAP_USER_ID_SUFFIX = "@????"; 
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		LOGGER.info("name >> " + auth.getName());
		LOGGER.info("principal >> " + auth.getPrincipal().toString());
		LOGGER.info("credential >> " + auth.getCredentials().toString());
		
		// step0. id, password 확인
		if ("".equals(auth.getPrincipal().toString()) || "".equals(auth.getCredentials().toString())) {
			LOGGER.warn("LDAP - Empty Credentials");
			throw new CustomBadCredentialsException("LDAP - Empty Credentials", auth.getPrincipal().toString());
		}
		
		// setp1. ILM 인증
		// ID, password 사용(ILM server)
		if (isLdapAuthenticated(auth.getPrincipal().toString(), auth.getCredentials().toString())) {
			
			// step2. 사용자 정보 조회
			// id 사용(Backoffice Database)
			UserDetails userDetail = customUserDetailService.loadUserByUsername(auth.getPrincipal().toString());
			
			// principal에 사용자 이름이 아닌 사용자 정보를 모두 넣는다.
			return new UsernamePasswordAuthenticationToken(userDetail, auth.getCredentials(), userDetail.getAuthorities());
		} else {
			LOGGER.warn("LDAP - Bad Credentials");
			throw new CustomBadCredentialsException("LDAP - Bad Credentials", auth.getPrincipal().toString());
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
	private boolean isLdapAuthenticated(String id, String password) {
		// 임시추가함 2017.07.28. by jonghyeon
		// superuser의 경우 인증 안함
//		if ("superusr".equalsIgnoreCase(id)) {
//			return true;
//		}
		
		DirContext ctx = null;
		
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.setProperty(Context.PROVIDER_URL, LDAP_SERVER);
		props.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
		props.setProperty(Context.SECURITY_PRINCIPAL, id + LDAP_USER_ID_SUFFIX); 
		props.setProperty(Context.SECURITY_CREDENTIALS, password); 

		try {
			ctx = new InitialDirContext(props);
			return true;
		} catch (NamingException e) {
			LOGGER.error("Fail authenticate\n" + e.getMessage());
			return false;
		} finally {
			if (null != ctx) {
				try {
					ctx.close();
				} catch (NamingException e) {
					//e.printStackTrace();
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

}
