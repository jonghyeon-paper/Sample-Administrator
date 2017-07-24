package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.user.User;
import com.skplanet.iba.domain.user.UserAuthority;
import com.skplanet.iba.domain.user.UserAuthorityService;
import com.skplanet.iba.domain.user.UserService;
import com.skplanet.iba.support.enumdata.UseState;

public class UserTest extends AbstractJUnit {
	
	static final String USER_ID = "TestSuperUser@partner.sk.com";
	static final String[] AUTHORITY_ID = {"TEST_ROLE_SUPERUSER", "TEST_ROLE_NOTHING"};

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthorityService userAuthorityService;
	
	@Test
	public void get() {
		User userSearchCondition = new User();
		List<User> result = userService.retrieveList(userSearchCondition);
		print(result);
	}
	
	//@Test
	@Transactional
	public void add() {
		User user = new User();
		user.setUserId(USER_ID);
		user.setUserName("gildong");
		user.setUseState(UseState.USE);
		userService.add(user);
		
		List<UserAuthority> userAuthorityList = new ArrayList<>();
		for (String authorityId : Arrays.asList(AUTHORITY_ID)) {
			UserAuthority userAuthority = new UserAuthority();
			userAuthority.setUserId(USER_ID);
			userAuthority.setAuthorityId(authorityId);
			userAuthorityList.add(userAuthority);
		}
		userAuthorityService.add(userAuthorityList);
		
		User userSearchCondition = new User();
		userSearchCondition.setUserId(USER_ID);
		User result = userService.retrieve(userSearchCondition);
		print(result);
		
		//remove
		//remove();
	}
	
	//@Test
	public void edit() {
		User user = new User();
		user.setUserId(USER_ID);
		user.setUseState(UseState.UNUSE);
		userService.edit(user);
	}
	
	//@Test
	public void remove() {
		userAuthorityService.removeByUserId(USER_ID);
		userService.removeByUserId(USER_ID);
		
		User userSearchCondition = new User();
		userSearchCondition.setUserId(USER_ID);
		User result = userService.retrieve(userSearchCondition);
		print(result);
	}
}
