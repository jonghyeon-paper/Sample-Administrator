package com.sample.administrator.controller.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.administrator.model.authority.AuthorityService;
import com.sample.administrator.model.authority.entity.Authority;
import com.sample.administrator.model.authority.entity.element.AccessMode;
import com.sample.administrator.web.element.ResponseCode;
import com.sample.administrator.web.entity.AjaxResponse;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;
	
	@GetMapping("/view.do")
	public String view() {
		return "/authority/view.page";
	}
	
	@PostMapping("/info.do")
	@ResponseBody
	public Authority getAuthorityInfo(@RequestBody Authority authority) {
		return authorityService.retrieve(authority);
	}
	
	@PostMapping("/list.do")
	@ResponseBody
	public List<Authority> getAuthorityList(@RequestBody Authority authority) {
		return authorityService.retrieveList(authority);
	}
	
	@PostMapping("/add.do")
	@ResponseBody
	public AjaxResponse addAuthority(@RequestBody Authority authority) {
		Boolean flag = authorityService.add(authority);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		return response;
	}
	
	@PostMapping("/edit.do")
	@ResponseBody
	public AjaxResponse editAuthority(@RequestBody Authority authority) {
		Boolean flag = authorityService.edit(authority);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		return response;
	}
	
	@PostMapping("/remove.do")
	@ResponseBody
	public AjaxResponse removeAuthority(@RequestBody Authority authority) {
		Boolean flag = authorityService.remove(authority);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		return response;
	}
	
	@PostMapping("/access/list.do")
	@ResponseBody
	public List<Map<String, String>> getAuthorityAccessList() {
		List<Map<String, String>> list = new ArrayList<>();
		for (AccessMode item : AccessMode.values()) {
			list.add(item.getJson());
		}
		return list;
	}
}
