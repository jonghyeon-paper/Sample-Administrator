package com.skplanet.iba.web.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	
//	@Autowired
//	private FilterChainProxy filterChainProxy;
	
	@RequestMapping("/login.do")
	public String login() {
		return "/login";
	}
	
	@RequestMapping("/main.do")
	public String main() {
		return "/main.page";
	}
	
	@RequestMapping("/denied.do")
	public String denied() {
		return "/denied";
	}

//	@RequestMapping("/filterchain.do")
//	public @ResponseBody
//	Map<Integer, Map<Integer, String>> getSecurityFilterChainProxy(){
//		Map<Integer, Map<Integer, String>> filterChains= new HashMap<Integer, Map<Integer, String>>();
//		int i = 1;
//		for(SecurityFilterChain secfc :  this.filterChainProxy.getFilterChains()){
//			//filters.put(i++, secfc.getClass().getName());
//			Map<Integer, String> filters = new HashMap<Integer, String>();
//			int j = 1;
//			for(Filter filter : secfc.getFilters()){
//				filters.put(j++, filter.getClass().getName());
//			}
//			filterChains.put(i++, filters);
//		}
//		return filterChains;
//	}
}
