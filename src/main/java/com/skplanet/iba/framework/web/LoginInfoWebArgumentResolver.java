package com.skplanet.iba.framework.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.skplanet.iba.domain.user.LoginInfo;
import com.skplanet.iba.domain.user.User;

public class LoginInfoWebArgumentResolver implements HandlerMethodArgumentResolver{
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
			WebDataBinderFactory webDataBinderFactory) throws Exception {
		HttpSession session = ((HttpServletRequest) nativeWebRequest.getNativeRequest()).getSession();
		User user = (User) session.getAttribute("LOGIN_SESSION");
		return new LoginInfo(user);
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return LoginInfo.class.isAssignableFrom(methodParameter.getParameterType());
	}
}
