package com.sample.administrator.security.interceptor.voter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

public class UriBasedVoter implements AccessDecisionVoter<FilterInvocation> {
	
	final static String URI_MAIN = "/main.do";
	final static String URI_LOGIN = "/login.do";
	final static String URI_LOGOUT = "/logout.do";
	
	final static String ROLE_EMPTY = "ROLE_EMPTY";
	
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
			// ROLE_EMPTY는 검사하지 않는다.
			if (ROLE_EMPTY.equals(userHasRole)) {
				continue;
			}
			for (ConfigAttribute urIAuthority : attributes) {
				String uriHasRole = urIAuthority.getAttribute().toString();
				// ROLE_EMPTY는 검사하지 않는다.
				if (ROLE_EMPTY.equals(uriHasRole)) {
					continue;
				}
				if (userHasRole.equals(uriHasRole)) {
					// access role insert to request
					addUriAccessRole(filterInvocation, userHasRole);
					
					return ACCESS_GRANTED;
				}
			}
		}
		return ACCESS_ABSTAIN;
	}
	
	private void addUriAccessRole(FilterInvocation filterInvocation, String role) {
		HttpServletRequest request = filterInvocation.getRequest();
		@SuppressWarnings("unchecked")
		List<String> uriAccessRoles = (List<String>) request.getAttribute("uriAccessRole");
		if (uriAccessRoles == null) {
			uriAccessRoles = new ArrayList<>();
		}
		uriAccessRoles.add(role);
		request.setAttribute("uriAccessRole", uriAccessRoles);
	}

}
