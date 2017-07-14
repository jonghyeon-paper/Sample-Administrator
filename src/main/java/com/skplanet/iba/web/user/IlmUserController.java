package com.skplanet.iba.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.iba.domain.user.IlmUser;
import com.skplanet.iba.domain.user.IlmUserService;

@Controller
@RequestMapping("/ilmuser")
public class IlmUserController {

	@Autowired
	private IlmUserService ilmuserService;
	
	@RequestMapping(value = "/list.do", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public List<IlmUser> getUserList(@RequestBody IlmUser ilmUser) {
		return ilmuserService.retrieveList(ilmUser);
	}
	
}
