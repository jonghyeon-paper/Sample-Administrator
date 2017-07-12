<%@ page contentType="text/html; charset=utf-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
	<title>Main Page</title>
</head>
<body>
	<h3>Welcome!!</h3>
	
	<p>principal : <sec:authentication property="principal"/></p>
	<p>principal.username : <sec:authentication property="principal.username"/></p>
	<p>principal.enabled : <sec:authentication property="principal.enabled"/></p>
	<p>principal.accountNonExpired : <sec:authentication property="principal.accountNonExpired"/></p>
	<p><button type="button" onclick="document.location.href='${pageContext.request.contextPath}/logout.do';return false;">logout</button></p>
</body>
</html>