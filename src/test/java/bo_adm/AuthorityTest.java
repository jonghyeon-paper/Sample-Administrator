package bo_adm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.authority.Authority;
import com.skplanet.iba.domain.authority.AuthorityAccessMode;
import com.skplanet.iba.domain.authority.AuthorityAccessModeService;
import com.skplanet.iba.domain.authority.AuthorityMenu;
import com.skplanet.iba.domain.authority.AuthorityMenuService;
import com.skplanet.iba.domain.authority.AuthorityService;
import com.skplanet.iba.domain.authority.enumdata.AccessMode;
import com.skplanet.iba.domain.menu.Menu;
import com.skplanet.iba.domain.menu.MenuService;
import com.skplanet.iba.share.enumdata.UseState;

public class AuthorityTest extends AbstractJUnit {

	static final String AUTHORITY_ID = "TEST_ROLE_SUPERUSER";
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private AuthorityMenuService authorityMenuService;
	
	@Autowired
	private AuthorityAccessModeService authorityAccessModeService;
	
	@Autowired
	private MenuService menuService;
	
	//@Test
	public void get() {
		Authority authoritySearchCondition = new Authority();
		authoritySearchCondition.setAuthorityId(AUTHORITY_ID);
		Authority result = authorityService.retrieveAuthority(authoritySearchCondition);
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
		authorityService.addAuthority(supseruser);
		
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
			authorityMenuService.addAuthorityMenu(authorityMenuList);
		}
		
		// Add authority access mode
		AuthorityAccessMode readMode = new AuthorityAccessMode();
		readMode.setAuthorityId(AUTHORITY_ID);
		readMode.setAccessMode(AccessMode.READ);
		
		AuthorityAccessMode writedMode = new AuthorityAccessMode();
		writedMode.setAuthorityId(AUTHORITY_ID);
		writedMode.setAccessMode(AccessMode.WRITE);
		
		AuthorityAccessMode excuteMode = new AuthorityAccessMode();
		excuteMode.setAuthorityId(AUTHORITY_ID);
		excuteMode.setAccessMode(AccessMode.EXCUTE);
		
		List<AuthorityAccessMode> authorityAccessModeList = new ArrayList<>();
		authorityAccessModeList.add(readMode);
		authorityAccessModeList.add(writedMode);
		authorityAccessModeList.add(excuteMode);
		authorityAccessModeService.addAuthorityAccessMode(authorityAccessModeList);
		
		Authority authoritySearchCondition = new Authority();
		authoritySearchCondition.setAuthorityId(AUTHORITY_ID);
		Authority result = authorityService.retrieveAuthority(authoritySearchCondition);
		print(result);
		
		// remove test data - reverse process
		//remove();
	}
	
	//@Test
	public void edit() {
	}
	
	//@Test
	public void hierarchy() {
	}
	
	//@Test
	public void remove() {
		authorityAccessModeService.removeAuthorityAccessModeByAuthorityId(AUTHORITY_ID);
		authorityMenuService.removeAuthorityMenuByAuthorityId(AUTHORITY_ID);
		authorityService.removeAuthorityByAuthorityId(AUTHORITY_ID);
		
		Authority authoritySearchCondition = new Authority();
		authoritySearchCondition.setAuthorityId(AUTHORITY_ID);
		Authority result = authorityService.retrieveAuthority(authoritySearchCondition);
		print(result);
	}
}
