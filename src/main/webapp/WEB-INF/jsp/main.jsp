<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<h3>Welcome!!</h3>
	
	<p>principal : <sec:authentication property="principal"/></p>
	<p>principal.username : <sec:authentication property="principal.username"/></p>
	<p>principal.enabled : <sec:authentication property="principal.enabled"/></p>
	<p>principal.accountNonExpired : <sec:authentication property="principal.accountNonExpired"/></p>
	<p>principal.authorities : <sec:authentication property="principal.authorities"/></p>
	
	<p>principal.customUserData.userId : <sec:authentication property="principal.customUserData.userId"/></p>
	<p>principal.customUserData.userName : <sec:authentication property="principal.customUserData.userName"/></p>
	<p>principal.customUserData.useState : <sec:authentication property="principal.customUserData.useState"/></p>
	
	<p><button type="button" onclick="document.location.href='${pageContext.request.contextPath}/logout.do';return false;">logout</button></p>
