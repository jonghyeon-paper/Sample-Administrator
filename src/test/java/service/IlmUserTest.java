package service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.iba.domain.user.IlmUser;
import com.skplanet.iba.domain.user.IlmUserService;

public class IlmUserTest extends AbstractJUnit {
	
	@Autowired
	private IlmUserService ilmUserService;
	
	//@Test
	public void get() {
		IlmUser ilmUserSearchCondition = new IlmUser();
		List<IlmUser> result = ilmUserService.retrieveList(ilmUserSearchCondition);
		print(result);
	}
	
}
