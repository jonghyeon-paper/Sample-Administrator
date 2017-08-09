package com.sample.administrator.controller.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.sample.administrator.core.exception.BusinessException;

@Controller
@ControllerAdvice
public class DefaultExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public ModelAndView businessException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		return getModelAndView(request, response, exception);
	}
	
	@ExceptionHandler()
	public ModelAndView uriException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		return getModelAndView(request, response, exception);
	}
	
	public ModelAndView ajaxExcpetion() {
		return null;
	}
	
	private ModelAndView getModelAndView(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		ModelAndView modelAndView = new ModelAndView();
		String exceptionMessage = exception.getMessage();
		if (request != null && request.getHeader("Accept") != null && request.getHeader("Accept").contains("json")) {
			modelAndView.setView(new MappingJackson2JsonView());
			modelAndView.addObject("exception", exceptionMessage);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} else {
			modelAndView.setViewName("/comon/error");
			String refererURI = request != null ? request.getHeader("Referer") : "";
			if (refererURI != null && refererURI.endsWith("login")) {
				refererURI = "/";
			}
			modelAndView.addObject("forwardURI", refererURI);
			modelAndView.addObject("message", exceptionMessage);
		}
	
		return modelAndView;
	}
}
