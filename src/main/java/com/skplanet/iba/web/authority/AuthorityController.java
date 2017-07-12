package com.skplanet.iba.web.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.iba.domain.authority.Authority;
import com.skplanet.iba.domain.authority.AuthorityService;
import com.skplanet.iba.share.enumdata.ResponseCode;
import com.skplanet.iba.share.model.AjaxResponse;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;
	
	@GetMapping("/view.do")
	public String view() {
		return "/authority/view";
	}
	
	@PostMapping("/info.do")
	@ResponseBody
	public Authority getAuthorityInfo(@RequestBody Authority authority) {
		return authorityService.retrieveAuthority(authority);
	}
	
	@PostMapping("/list.do")
	@ResponseBody
	public List<Authority> getAuthorityList(@RequestBody Authority authority) {
		return authorityService.retrieveList(authority);
	}
	
	@PostMapping("/add.do")
	@ResponseBody
	public AjaxResponse addAuthority(@RequestBody Authority authority) {
		Boolean flag = authorityService.addAuthority(authority);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
	
	@PostMapping("/edit.do")
	@ResponseBody
	public AjaxResponse editAuthority(@RequestBody Authority authority) {
		Boolean flag = authorityService.editAuthority(authority);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
	
	@PostMapping("/remove.do")
	@ResponseBody
	public AjaxResponse removeAuthority(@RequestBody Authority authority) {
		Boolean flag = authorityService.removeAuthority(authority);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
}
