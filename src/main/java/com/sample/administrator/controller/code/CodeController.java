package com.sample.administrator.controller.code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.administrator.model.code.CodeService;
import com.sample.administrator.model.code.entity.Code;
import com.sample.administrator.web.element.ResponseCode;
import com.sample.administrator.web.entity.AjaxResponse;

@Controller
@RequestMapping("/code")
public class CodeController {

	@Autowired
	private CodeService codeService;
	
	@GetMapping("/view.do")
	public String view() {
		return "/code/view.page";
	}
	
	@PostMapping("/info.do")
	@ResponseBody
	public Code getCodeInfo(@RequestBody Code code) {
		return codeService.retrieve(code);
	}
	
	@PostMapping("/list.do")
	@ResponseBody
	public List<Code> getCodeList(@RequestBody Code code) {
		return codeService.retrieveList(code);
	}
	
	@PostMapping("/add.do")
	@ResponseBody
	public AjaxResponse addCode(@RequestBody Code code) {
		Boolean flag = codeService.add(code);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
	
	@PostMapping("/edit.do")
	@ResponseBody
	public AjaxResponse editCode(@RequestBody Code code) {
		Boolean flag = codeService.edit(code);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
	
	@PostMapping("/remove.do")
	@ResponseBody
	public AjaxResponse removeCode(@RequestBody Code code) {
		Boolean flag = codeService.remove(code);
		
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(flag ? ResponseCode.SUCCESS : ResponseCode.FAIL);
		response.setResponseMessage(response.getResponseCode().getDescription());
		return response;
	}
	
	@PostMapping("/hierarchy.do")
	@ResponseBody
	public Code getCodeHierarchy(@RequestBody Code code) {
		return codeService.getCodeHierarchy(code);
	}
}
