package com.skplanet.iba.security.intercept.voter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

public class AnonymousUserVoter implements AccessDecisionVoter<FilterInvocation> {

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
		
		if (!authentication.isAuthenticated()) {
			return ACCESS_DENIED;
		}
		
		if (authentication.getPrincipal() == null) {
			return ACCESS_DENIED;
		}
		
		if (authentication.getAuthorities() == null) {
			return ACCESS_DENIED;
		}
		
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			if ("ROLE_ANONYMOUS".equals(grantedAuthority.getAuthority())) {
				return ACCESS_ABSTAIN;
			}
		}
		
		return ACCESS_ABSTAIN;
	}

}
