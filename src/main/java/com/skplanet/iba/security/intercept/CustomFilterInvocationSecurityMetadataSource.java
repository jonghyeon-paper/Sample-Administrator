package com.skplanet.iba.security.intercept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.skplanet.iba.domain.authority.AuthorityMenu;
import com.skplanet.iba.domain.authority.AuthorityMenuService;
import com.skplanet.iba.domain.menu.Menu;
import com.skplanet.iba.domain.menu.MenuService;
import com.skplanet.iba.share.enumdata.UseState;

public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException 
	{
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String uri = filterInvocation.getRequestUrl();
		String method = filterInvocation.getRequest().getMethod();
		String[] roles = null;
		
		// temporary data
		/*
		roles = new String[] {"ROLE_1", "ROLE_2"};
		return SecurityConfig.createList(roles);
		 */
		
		Menu menuSearchCondition = new Menu();
		menuSearchCondition.setUseState(UseState.USE);
		List<Menu> availableMenuList = menuService.retrieveList(menuSearchCondition);
		
		if ("get".equalsIgnoreCase(method)) {
			// get이면 uri 비교
			// 없으면 다음 단계
			for (Menu item : availableMenuList) {
				if (!uri.equals(item.getUri())) {
					continue;
				}
				
				AuthorityMenu authorityMenuSearchCondition = new AuthorityMenu();
				authorityMenuSearchCondition.setMenuId(item.getMenuId());
				List<AuthorityMenu> targetAuthorityMenuList = authorityMenuService.retrieveList(authorityMenuSearchCondition);
				roles = createRoleArray(targetAuthorityMenuList);
				break;
			}
		}
		
		if (!"get".equalsIgnoreCase(method)) {
			// get이 아니면 uri의 마지막절 제외후 indexof 비교
			// 없으면 다음 단계
			Pattern uriPattern = Pattern.compile("(/[^\\s.]+/)");
			Matcher uriMatcher = uriPattern.matcher(uri);
			if (uriMatcher.find()) {
				String parentUri = uriMatcher.group();
				for (Menu item : availableMenuList) {
					if (item.getUri().indexOf(parentUri) == -1) {
						continue;
					}
					
					AuthorityMenu authorityMenuSearchCondition = new AuthorityMenu();
					authorityMenuSearchCondition.setMenuId(item.getMenuId());
					List<AuthorityMenu> targetAuthorityMenuList = authorityMenuService.retrieveList(authorityMenuSearchCondition);
					roles = createRoleArray(targetAuthorityMenuList);
					break;
				}
			}
		}
		
		if (roles == null) {
			// 없으면 
			// 대상 테이블 의존 uri(menu_dependence_uri 비교) - 미구현
			// 없으면 빈 객체
			roles = new String[] {"ROLE_NOTHING"};
		}
		
		return SecurityConfig.createList(roles);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	private String[] createRoleArray(List<AuthorityMenu> authorityMenuList) {
		List<String> result = new ArrayList<>();
		for (AuthorityMenu item : authorityMenuList) {
			result.add(item.getAuthorityId());
		}
		String[] array = new String[result.size()];
		return result.toArray(array);
	}

}
