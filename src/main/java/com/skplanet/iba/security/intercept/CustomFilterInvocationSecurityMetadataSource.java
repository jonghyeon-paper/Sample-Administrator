package com.skplanet.iba.security.intercept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.skplanet.iba.domain.authority.Authority;

public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException 
	{
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String uri = filterInvocation.getRequestUrl();
		String method = filterInvocation.getRequest().getMethod();
		
		String[] roles = null;
		
		if ("get".equalsIgnoreCase(method)) {
			// get이면 uri 비교
			// 없으면 다음 단계
			// Menu targetMenu = menuService.retrieveMenu(uri);
			// Authority targetAuthorityMenuList = authorityService.retrieveList(targetMenu.getMenuId());
			List<Authority> targetAuthorityList = new ArrayList<>();
			roles = createRoleArray(targetAuthorityList);
		}
		
		if (!"get".equalsIgnoreCase(method)) {
			// get이 아니면 uri의 마지막절 제외후 indexof 비교
			// 대상 테이블 메뉴
			// 없으면 다음 단계
			// List<Authority> targetAuthorityList = authorityService.retrieveList(조건?);
			List<Authority> targetAuthorityList = new ArrayList<>();
			roles = createRoleArray(targetAuthorityList);
		}
		
		if (roles == null) {
			// 없으면 
			// 대상 테이블 의존 uri(menu_dependence_uri 비교)
			// 없으면 빈 객체
			
			
		}
		
		//return roles;
		
		// temporary data
		roles = new String[] {"ROLE_1", "ROLE_2"};
		return SecurityConfig. createList(roles);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	private String[] createRoleArray(List<Authority> authorityList) {
		List<String> result = new ArrayList<>();
		for (Authority item : authorityList) {
			result.add(item.getAuthorityId());
		}
		String[] array = new String[result.size()];
		return result.toArray(array);
	}

}
