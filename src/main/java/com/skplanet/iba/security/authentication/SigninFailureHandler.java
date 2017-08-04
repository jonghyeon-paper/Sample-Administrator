package com.skplanet.iba.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.skplanet.iba.domain.login.LoginHistory;
import com.skplanet.iba.domain.login.LoginHistoryService;
import com.skplanet.iba.security.authentication.customization.CustomBadCredentialsException;

public class SigninFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	private LoginHistoryService loginHistoryService; 

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		if (exception.getClass().isAssignableFrom(CustomBadCredentialsException.class)) {
			
			CustomBadCredentialsException e = (CustomBadCredentialsException) exception;
			
			// history
			loginHistoryService.add(new LoginHistory(e.getAccessUserId(), false, e.getMessage()));
		}
		
		super.onAuthenticationFailure(request, response, exception);
	}
}
