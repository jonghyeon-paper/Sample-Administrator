package com.skplanet.iba.web.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.iba.domain.menu.Menu;
import com.skplanet.iba.domain.menu.MenuService;
import com.skplanet.iba.share.enumdata.ResponseCode;
import com.skplanet.iba.share.model.AjaxResponse;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@GetMapping("/view.do")
	public String view() {
		return "/menu/view.page";
	}
	
	@GetMapping("/viewpart.do")
	public String view2() {
		return "/menu/view.part";
	}
	
	@PostMapping("/hierarchy")
	@ResponseBody
	public Menu getHierarchy(@RequestBody Menu menu) {
		return menuService.getHierarchyMenu();
	}
	
	@PostMapping("/info.do")
	@ResponseBody
	public Menu getMenuInfo(@RequestBody Menu menu) {
		return menuService.retrieveMenu(menu);
	}
	
	@PostMapping("/list.do")
	@ResponseBody
	public List<Menu> getMenuList(@RequestBody Menu menu) {
		return menuService.retrieveList(menu);
	}
	
	@PostMapping("/add.do")
	@ResponseBody
	public AjaxResponse addMenu(@RequestBody Menu menu) {
		Boolean flag = menuService.addMenu(menu);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
	
	@PostMapping("/edit.do")
	@ResponseBody
	public AjaxResponse editMenu(@RequestBody Menu menu) {
		Boolean flag = menuService.editMenu(menu);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
	
	@PostMapping("/remove.do")
	@ResponseBody
	public AjaxResponse removeMenu(@RequestBody Menu menu) {
		Boolean flag = menuService.removeMenu(menu);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
}
