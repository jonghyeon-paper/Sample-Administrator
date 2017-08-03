package com.skplanet.iba.security.authentication;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.skplanet.iba.domain.authority.AuthorityMenu;
import com.skplanet.iba.domain.authority.AuthorityMenuService;
import com.skplanet.iba.domain.menu.Menu;
import com.skplanet.iba.domain.menu.MenuService;
import com.skplanet.iba.domain.user.User;
import com.skplanet.iba.security.authentication.customization.CustomUserDetail;
import com.skplanet.iba.support.enumdata.UseState;

public class SigninSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	@Autowired
	private MenuService menuService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication)throws IOException, ServletException {
		
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
		
		CustomUserDetail UserDetails = (CustomUserDetail) authentication.getPrincipal();
		User user = (User) UserDetails.getCustomUserData();
		
		@SuppressWarnings("unchecked")
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
		if (authorities == null || authorities.isEmpty()) {
			user.setAccessibleMenu(new Menu());
			return;
		}
		
		HashSet<String> temporaryAuthority = new HashSet<>();
		if (authorities != null) {
			for (SimpleGrantedAuthority item : authorities) {
				/* 미사용 2017.07.25.
				temporaryAuthority.add(item.getAuthority().toString().replaceAll("(_READ)|(_WRITE)|(_EXCUTE)$", ""));
				 */
				temporaryAuthority.add(item.getAuthority().toString());
			}
		}
		List<AuthorityMenu> targetAuthorityMenuList = authorityMenuService.retrieveListByAuthorityIds(temporaryAuthority.toArray(new String[temporaryAuthority.size()]));
		if (targetAuthorityMenuList == null || targetAuthorityMenuList.isEmpty()) {
			user.setAccessibleMenu(new Menu());
			return;
		}
		
		HashSet<Integer> temporaryMenu = new HashSet<>();
		if (targetAuthorityMenuList != null) {
			for (AuthorityMenu item : targetAuthorityMenuList) {
				temporaryMenu.add(item.getMenuId());
			}
		}
		List<Menu> accessableMenuList = menuService.retrieveListByMenuIds(temporaryMenu.toArray(new Integer[temporaryMenu.size()]));
		// 미사용 메뉴는 삭제 // 위 검색 조건에 사용여부 조건을 줄 수 없어서 어플리케이션에서 확인하여 제거.
		for (int i = accessableMenuList.size() - 1; i > -1; i--) {
			if (UseState.UNUSE.equals(accessableMenuList.get(i).getUseState())) {
				accessableMenuList.remove(i);
			}
		}
		Menu hierarchyMenu = menuService.createMenuHierarchy(accessableMenuList);
		user.setAccessibleMenu(hierarchyMenu);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
