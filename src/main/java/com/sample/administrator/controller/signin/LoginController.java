package com.sample.administrator.controller.signin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
public class LoginController {
	
//	@Autowired
//	private FilterChainProxy filterChainProxy;
	
	@RequestMapping("/login.do")
	public String login() {
		return "/login.empty";
	}
	
	@RequestMapping("/main.do")
	public String main() {
		return "/main.page";
	}
	
	@RequestMapping("/denied/{path}.do")
	public ModelAndView deniedForward(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("path") String path) throws IOException {
		
		switch (path) {
			case "403":
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				break;
			default :
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				break;
		}
		
		ModelAndView mav = new ModelAndView();
		if (request != null && request.getHeader("Accept") != null && request.getHeader("Accept").contains("json")) {
			mav.setView(new MappingJackson2JsonView());
			mav.addObject(new HashMap<>());
		} else {
			mav.setViewName("/denied");
		}
		return mav;
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
