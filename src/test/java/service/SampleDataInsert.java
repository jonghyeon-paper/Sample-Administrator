package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.authority.Authority;
import com.skplanet.iba.domain.authority.AuthorityAccess;
import com.skplanet.iba.domain.authority.AuthorityAccessService;
import com.skplanet.iba.domain.authority.AuthorityMenu;
import com.skplanet.iba.domain.authority.AuthorityMenuService;
import com.skplanet.iba.domain.authority.AuthorityService;
import com.skplanet.iba.domain.authority.enumdata.AccessMode;
import com.skplanet.iba.domain.menu.Menu;
import com.skplanet.iba.domain.menu.MenuService;
import com.skplanet.iba.domain.user.User;
import com.skplanet.iba.domain.user.UserAuthority;
import com.skplanet.iba.domain.user.UserAuthorityService;
import com.skplanet.iba.domain.user.UserService;
import com.skplanet.iba.support.enumdata.UseState;

public class SampleDataInsert extends AbstractJUnit {
	
	static final String USER_ID = "superuser";
	static final String AUTHORITY_ID = "ROLE_SUPER";
	static final String AUTHORITY_NOTHING_ID = "ROLE_NOTHING";
	static final String[] AUTHORITY_IDS = {"ROLE_SUPER"};
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthorityService userAuthorityService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	@Autowired
	private AuthorityAccessService authorityAccessModeService;
	
	@Autowired
	private MenuService menuService;
	
	//@Test
	//@Transactional
	public void add() {
		
		// Add menu
		Menu systemManage = new Menu();
		systemManage.setMenuName("시스템");
		systemManage.setUseState(UseState.USE);
		menuService.add(systemManage);
		
		Menu userManage = new Menu();
		userManage.setMenuName("사용자 관리");
		userManage.setUseState(UseState.USE);
		userManage.setUri("user/listview.do");
		userManage.setParentMenuId(systemManage.getMenuId());
		
		Menu authorityManage = new Menu();
		authorityManage.setMenuName("권한 관리");
		authorityManage.setUseState(UseState.USE);
		authorityManage.setUri("authority/view.do");
		authorityManage.setParentMenuId(systemManage.getMenuId());
		
		Menu menuManage = new Menu();
		menuManage.setMenuName("메뉴 관리");
		menuManage.setUseState(UseState.USE);
		menuManage.setUri("menu/view.do");
		menuManage.setParentMenuId(systemManage.getMenuId());
		
		Menu codeManage = new Menu();
		codeManage.setMenuName("코드 관리");
		codeManage.setUseState(UseState.USE);
		codeManage.setUri("code/view.do");
		codeManage.setParentMenuId(systemManage.getMenuId());
		
		List<Menu> menuList = new ArrayList<>();
		menuList.add(userManage);
		menuList.add(authorityManage);
		menuList.add(menuManage);
		menuList.add(codeManage);
		menuService.add(menuList);
		
		/************************************************************************/
		
		// generate authority menu - 메뉴를 먼저 등록해야함!!!
		Menu menuSearchCondition = new Menu();
		menuSearchCondition.setUseState(UseState.USE);
		List<Menu> availableMenuList = menuService.retrieveList(menuSearchCondition);
		List<AuthorityMenu> authorityMenuList = new ArrayList<>();
		for (Menu item : availableMenuList) {
			AuthorityMenu authorityMenu = new AuthorityMenu();
			authorityMenu.setAuthorityId(AUTHORITY_ID);
			authorityMenu.setMenuId(item.getMenuId());
			authorityMenuList.add(authorityMenu);
		}
		
		// generate authority access
		AuthorityAccess readMode = new AuthorityAccess();
		readMode.setAuthorityId(AUTHORITY_ID);
		readMode.setAccessMode(AccessMode.READ);
		
		AuthorityAccess writedMode = new AuthorityAccess();
		writedMode.setAuthorityId(AUTHORITY_ID);
		writedMode.setAccessMode(AccessMode.WRITE);
		
		AuthorityAccess excuteMode = new AuthorityAccess();
		excuteMode.setAuthorityId(AUTHORITY_ID);
		excuteMode.setAccessMode(AccessMode.EXCUTE);
		
		List<AuthorityAccess> authorityAccessList = new ArrayList<>();
		authorityAccessList.add(readMode);
		authorityAccessList.add(writedMode);
		authorityAccessList.add(excuteMode);
		
		// Add authority
		Authority superuser = new Authority();
		superuser.setAuthorityId(AUTHORITY_ID);
		superuser.setAuthorityName("슈퍼사용자");
		superuser.setDescription("슈퍼사용자!@#");
		superuser.setUseState(UseState.USE);
		superuser.setAuthorityMenuList(authorityMenuList);
		superuser.setAuthorityAccessList(authorityAccessList);
		authorityService.add(superuser);
		
		/************************************************************************/
		
		// generate user authority
		List<UserAuthority> userAuthorityList = new ArrayList<>();
		for (String authorityId : Arrays.asList(AUTHORITY_IDS)) {
			UserAuthority userAuthority = new UserAuthority();
			userAuthority.setUserId(USER_ID);
			userAuthority.setAuthorityId(authorityId);
			userAuthorityList.add(userAuthority);
		}
		
		// Add user
		User user = new User();
		user.setUserId(USER_ID);
		user.setUserName("슈퍼사용자");
		user.setDepartment("없음");
		user.setUseState(UseState.USE);
		user.setUserAuthorityList(userAuthorityList);
		userService.add(user);
	}
	
	//@Test
	public void remove() {
		
		// Remove menu - 메뉴는 자동증가키값이라 불가능
		
		// remove user
		userAuthorityService.removeByUserId(USER_ID);
		userService.removeByUserId(USER_ID);
		
		// Remove authority
		authorityAccessModeService.removeByAuthorityId(AUTHORITY_ID);
		authorityMenuService.removeByAuthorityId(AUTHORITY_ID);
		authorityService.removeByAuthorityId(AUTHORITY_ID);
	}
	
	@Test
	public void addNothingAuthority() {
		
		// Add nothing authority
		Authority nothinguser = new Authority();
		nothinguser.setAuthorityId(AUTHORITY_NOTHING_ID);
		nothinguser.setAuthorityName("권한없는사용자");
		nothinguser.setDescription("권한없는사용자!@#");
		nothinguser.setUseState(UseState.USE);
		authorityService.add(nothinguser);
	}
}
