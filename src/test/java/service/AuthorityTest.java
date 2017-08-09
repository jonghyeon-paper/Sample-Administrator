package service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sample.administrator.model.authority.AuthorityAccessService;
import com.sample.administrator.model.authority.AuthorityMenuService;
import com.sample.administrator.model.authority.AuthorityService;
import com.sample.administrator.model.authority.entity.Authority;
import com.sample.administrator.model.authority.entity.AuthorityAccess;
import com.sample.administrator.model.authority.entity.AuthorityMenu;
import com.sample.administrator.model.authority.entity.element.AccessMode;
import com.sample.administrator.model.menu.MenuService;
import com.sample.administrator.model.menu.entity.Menu;
import com.sample.administrator.web.element.UseState;

public class AuthorityTest extends AbstractJUnit {

	static final String AUTHORITY_ID = "TEST_ROLE_SUPERUSER";
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	@Autowired
	private AuthorityAccessService authorityAccessModeService;
	
	@Autowired
	private MenuService menuService;
	
	//@Test
	public void get() {
		Authority authoritySearchCondition = new Authority();
		authoritySearchCondition.setAuthorityId(AUTHORITY_ID);
		Authority result = authorityService.retrieve(authoritySearchCondition);
		print(result);
	}
	
	@Test
	@Transactional
	public void add() {
		// Add authority
		Authority supseruser = new Authority();
		supseruser.setAuthorityId(AUTHORITY_ID);
		supseruser.setAuthorityName("SuperUser");
		supseruser.setDescription("123");
		supseruser.setUseState(UseState.USE);
		authorityService.add(supseruser);
		
		// Add authority menu
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
		
		// Add authority access mode
		AuthorityAccess readMode = new AuthorityAccess();
		readMode.setAuthorityId(AUTHORITY_ID);
		readMode.setAccessMode(AccessMode.READ);
		
		AuthorityAccess writedMode = new AuthorityAccess();
		writedMode.setAuthorityId(AUTHORITY_ID);
		writedMode.setAccessMode(AccessMode.WRITE);
		
		List<AuthorityAccess> authorityAccessModeList = new ArrayList<>();
		authorityAccessModeList.add(readMode);
		authorityAccessModeList.add(writedMode);
		authorityAccessModeService.add(authorityAccessModeList);
		
		Authority authoritySearchCondition = new Authority();
		authoritySearchCondition.setAuthorityId(AUTHORITY_ID);
		Authority result = authorityService.retrieve(authoritySearchCondition);
		print(result);
		
		// remove test data - reverse process
		//remove();
		authorityService.removeByAuthorityId(AUTHORITY_ID);
	}
	
	//@Test
	public void edit() {
	}
	
	//@Test
	public void hierarchy() {
	}
	
	//@Test
	public void remove() {
		authorityAccessModeService.removeByAuthorityId(AUTHORITY_ID);
		authorityMenuService.removeByAuthorityId(AUTHORITY_ID);
		authorityService.removeByAuthorityId(AUTHORITY_ID);
		
		Authority authoritySearchCondition = new Authority();
		authoritySearchCondition.setAuthorityId(AUTHORITY_ID);
		Authority result = authorityService.retrieve(authoritySearchCondition);
		print(result);
	}
}
