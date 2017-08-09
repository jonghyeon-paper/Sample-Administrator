package com.skplanet.iba.security.intercept;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	final static String ROLE_EMPTY = "ROLE_EMPTY";
	
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
		
		// get 요청일 경우 파라미터 정보는 제외
		if (method.equalsIgnoreCase("get") && uri.indexOf("?") > -1) {
			uri = uri.substring(0, uri.indexOf("?"));
		}
		
		// temporary data
		/*
		roles = new String[] {"ROLE_1", "ROLE_2"};
		return SecurityConfig.createList(roles);
		 */
		
		String[] roles = null;
		
		// step1. 메뉴 URI 비교
		Menu menuSearchCondition = new Menu();
		menuSearchCondition.setUseState(UseState.USE);
		List<Menu> availableMenuList = menuService.retrieveList(menuSearchCondition);
		if (availableMenuList != null && !availableMenuList.isEmpty()) {
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
		if (roles != null && roles.length > 0) {
			return SecurityConfig.createList(roles);
		}
		
		// step2-1. URI의 마지막절 제외후 indexof 비교
		String[] likeSearchUriRoles = null;
		String[] dependenceUriRoles = null;
		
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
			likeSearchUriRoles = createRoleArray(targetAuthorityMenuList);
		}
		
		// step2-2. 의존 uri(menu_dependence 비교)
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
			List<AuthorityMenu> targetAuthorityMenuList = authorityMenuService.retrieveListByMenuIds(temporaryMenu.toArray(new Integer[temporaryMenu.size()]));
			dependenceUriRoles = createRoleArray(targetAuthorityMenuList);
		}
		roles = ArrayUtils.addAll(likeSearchUriRoles, dependenceUriRoles);
		if (roles != null && roles.length > 0) {
			roles = Arrays.stream(roles).distinct().toArray(String[]::new);
			return SecurityConfig.createList(roles);
		}
		
		// step3. 없으면 더미값
		// ROLE_EMPTY는 권한이 하나도 없을 때 주는 권한이다. 사용되는 곳은 없다.
		return SecurityConfig.createList(new String[] {ROLE_EMPTY});
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
		return result.toArray(new String[result.size()]);
	}

}
