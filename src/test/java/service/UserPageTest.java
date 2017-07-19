package service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.iba.domain.user.User;
import com.skplanet.iba.domain.user.UserService;
import com.skplanet.iba.framework.data.PagingContents;
import com.skplanet.iba.framework.data.PagingRequest;

public class UserPageTest extends AbstractJUnit {

	@Autowired
	public UserService userService;
	
	@Test
	public void get() {
//		PagingContents<User> result = userService.retrievePageList(new User(), new PagingRequest(1, 15));
		User params = new User();
		params.setUserId("TestSuperUser@partner.sk.com");
		PagingContents<User> result = userService.retrievePage(new PagingRequest(1, 15), params);
		print(result);
	}
	
}
