package com.skplanet.iba.security.authentication.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import com.skplanet.iba.domain.authority.AuthorityAccess;
import com.skplanet.iba.domain.authority.AuthorityAccessService;
import com.skplanet.iba.domain.authority.enumdata.AccessMode;

public class UriAccessPermissionFilter extends GenericFilterBean {
	
	@Autowired
	private AuthorityAccessService authorityAccessService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		List<AccessMode> uriAccessMode = new ArrayList<>();
		uriAccessMode.add(AccessMode.READ); // 기본값은 읽기
		
		@SuppressWarnings("unchecked")
		List<String> uriAccessRoles = (List<String>) request.getAttribute("uriAccessRole");
		if (uriAccessRoles != null) {
			for (String role : uriAccessRoles) {
				AuthorityAccess condition = new AuthorityAccess();
				condition.setAuthorityId(role);
				
				List<AuthorityAccess> result = authorityAccessService.retrieveList(condition);
				if (result == null || result.isEmpty()) {
					continue;
				}
				for (AuthorityAccess authorityAccess : result) {
					if (authorityAccess.getAccessMode() == null) {
						continue;
					}
					uriAccessMode.add(authorityAccess.getAccessMode());
				}
			}
		}
		request.setAttribute("uriAccessMode", uriAccessMode);
		
		chain.doFilter(request, response);
	}

}
