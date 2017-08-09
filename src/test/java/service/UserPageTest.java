package service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sample.administrator.core.archetype.entity.PagingContents;
import com.sample.administrator.core.archetype.entity.PageRequest;
import com.sample.administrator.model.user.UserService;
import com.sample.administrator.model.user.entity.User;

public class UserPageTest extends AbstractJUnit {

	@Autowired
	public UserService userService;
	
	@Test
	public void get() {
//		PagingContents<User> result = userService.retrievePageList(new User(), new PagingRequest(1, 15));
		User params = new User();
		params.setUserId("TestSuperUser@partner.sk.com");
		PagingContents<User> result = userService.retrievePage(params, new PageRequest(1, 15));
		print(result);
	}
	
}
