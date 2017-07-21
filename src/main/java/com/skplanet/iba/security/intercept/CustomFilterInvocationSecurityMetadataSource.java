package com.skplanet.iba.security.intercept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.skplanet.iba.domain.authority.AuthorityMenu;
import com.skplanet.iba.domain.authority.AuthorityMenuService;
import com.skplanet.iba.domain.menu.Menu;
import com.skplanet.iba.domain.menu.MenuDependence;
import com.skplanet.iba.domain.menu.MenuDependenceService;
import com.skplanet.iba.domain.menu.MenuService;
import com.skplanet.iba.support.enumdata.UseState;

public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	@Autowired
	private MenuDependenceService menuDependenceService;
	
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
			List<AuthorityMenu> targetAuthorityMenuList = new ArrayList<>();
			for (Menu item : availableMenuList) {
				if (!uri.equals(item.getUri())) {
					continue;
				}
				
				AuthorityMenu authorityMenuSearchCondition = new AuthorityMenu();
				authorityMenuSearchCondition.setMenuId(item.getMenuId());
				targetAuthorityMenuList.addAll(authorityMenuService.retrieveList(authorityMenuSearchCondition));
			}
			roles = createRoleArray(targetAuthorityMenuList);
		}
		
		if (!"get".equalsIgnoreCase(method)) {
			String[] linkSearchUriRoles = null;
			String[] dependenceUriRoles = null;
			
			// get이 아니면 uri의 마지막절 제외후 indexof 비교
			Pattern uriPattern = Pattern.compile("(/[^\\s.]+/)");
			Matcher uriMatcher = uriPattern.matcher(uri);
			if (uriMatcher.find()) {
				String parentUri = uriMatcher.group();
				List<AuthorityMenu> targetAuthorityMenuList = new ArrayList<>();
				for (Menu item : availableMenuList) {
					// 부모메뉴는 URI가 없음.
					if (item.getUri() == null) {
						continue;
					}
					
					if (item.getUri().indexOf(parentUri) == -1) {
						continue;
					}
					
					AuthorityMenu authorityMenuSearchCondition = new AuthorityMenu();
					authorityMenuSearchCondition.setMenuId(item.getMenuId());
					targetAuthorityMenuList.addAll(authorityMenuService.retrieveList(authorityMenuSearchCondition));
				}
				linkSearchUriRoles = createRoleArray(targetAuthorityMenuList);
			}
			
			// 대상 테이블 의존 uri(menu_dependence_uri 비교)
			MenuDependence menuDependenceSearchCondition = new MenuDependence();
			menuDependenceSearchCondition.setDependenceUri(uri);
			List<MenuDependence> targetMenuDependenceList = menuDependenceService.retrieveList(menuDependenceSearchCondition);
			
			if (targetMenuDependenceList != null && !targetMenuDependenceList.isEmpty()) {
				HashSet<Integer> temporaryMenu = new HashSet<>();
				if (targetMenuDependenceList != null) {
					for (MenuDependence item : targetMenuDependenceList) {
						temporaryMenu.add(item.getMenuId());
					}
				}
				Integer[] dummyIntegerArray = new Integer[temporaryMenu.size()];
				List<AuthorityMenu> targetAuthorityMenuList = authorityMenuService.retrieveListByMenuIds(temporaryMenu.toArray(dummyIntegerArray));
				dependenceUriRoles = createRoleArray(targetAuthorityMenuList);
			}
			
			roles = ArrayUtils.addAll(linkSearchUriRoles, dependenceUriRoles);
		}
		
		if (roles == null) {
			// 없으면 빈 객체
			roles = new String[] {};
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
