package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
	
	static final String USER_ID = "TestSuperUser@partner.sk.com";
	static final String AUTHORITY_ID = "TEST_ROLE_SUPERUSER";
	static final String[] AUTHORITY_IDS = {"TEST_ROLE_SUPERUSER", "TEST_ROLE_NOTHING"};

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
	public void add() {
		
		// Add menu
		Menu systemManage = new Menu();
		systemManage.setMenuName("System manage");
		systemManage.setUseState(UseState.USE);
		menuService.add(systemManage);
		
		Menu menuManage = new Menu();
		menuManage.setMenuName("Menu manage");
		menuManage.setUseState(UseState.USE);
		menuManage.setUri("menu/view.do");
		menuManage.setParentMenuId(systemManage.getMenuId());
		
		Menu userManage = new Menu();
		userManage.setMenuName("User manage");
		userManage.setUseState(UseState.USE);
		userManage.setUri("user/view.do");
		userManage.setParentMenuId(systemManage.getMenuId());
		
		Menu codeManage = new Menu();
		codeManage.setMenuName("Code manage");
		codeManage.setUseState(UseState.USE);
		codeManage.setUri("code/view.do");
		codeManage.setParentMenuId(systemManage.getMenuId());
		
		List<Menu> menuList = new ArrayList<>();
		menuList.add(menuManage);
		menuList.add(userManage);
		menuList.add(codeManage);
		menuService.add(menuList);
		
		// Add user
		User user = new User();
		user.setUserId(USER_ID);
		user.setUserName("gildong");
		user.setUseState(UseState.USE);
		userService.add(user);
		
		// Add user authority
		List<UserAuthority> userAuthorityList = new ArrayList<>();
		for (String authorityId : Arrays.asList(AUTHORITY_IDS)) {
			UserAuthority userAuthority = new UserAuthority();
			userAuthority.setUserId(USER_ID);
			userAuthority.setAuthorityId(authorityId);
			userAuthorityList.add(userAuthority);
		}
		userAuthorityService.add(userAuthorityList);
		
		// Add authority
		Authority supseruser = new Authority();
		supseruser.setAuthorityId(AUTHORITY_ID);
		supseruser.setAuthorityName("SuperUser");
		supseruser.setDescription("SuperUser blah~~blah~~");
		supseruser.setUseState(UseState.USE);
		authorityService.add(supseruser);
		
		// Add authority menu - 메뉴를 먼저 등록해야함!!!
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
		if (authorityMenuList.size() > 0) {
			authorityMenuService.add(authorityMenuList);
		}
		
		// Add authority access
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
		authorityAccessModeService.add(authorityAccessList);
		
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
}
