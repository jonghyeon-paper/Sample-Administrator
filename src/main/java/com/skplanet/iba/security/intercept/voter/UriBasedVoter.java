package com.skplanet.iba.security.intercept.voter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

public class UriBasedVoter implements AccessDecisionVoter<FilterInvocation> {
	
	final static String URI_MAIN = "/main.do";
	final static String URI_LOGIN = "/login.do";
	final static String URI_LOGOUT = "/logout.do";
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	@Override
	public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String uri = filterInvocation.getRequestUrl();
		
		if (URI_MAIN.equalsIgnoreCase(uri)) {
			for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				if ("ROLE_ANONYMOUS".equals(grantedAuthority.getAuthority())) {
					return ACCESS_DENIED;
				}
			}
			return ACCESS_GRANTED;
		}
		
		if (URI_LOGIN.equalsIgnoreCase(uri)) {
			return ACCESS_GRANTED;
		}
		
		if (URI_LOGOUT.equalsIgnoreCase(uri)) {
			return ACCESS_GRANTED;
		}
		
		for (GrantedAuthority userAuthority : authentication.getAuthorities()) {
			String userHasRole = userAuthority.getAuthority().toString();
			for (ConfigAttribute urIAuthority : attributes) {
				String uriHasRole = urIAuthority.getAttribute().toString();
				if (userHasRole.equals(uriHasRole)) {
					return ACCESS_GRANTED;
				}
			}
		}
		return ACCESS_ABSTAIN;
	}

}
