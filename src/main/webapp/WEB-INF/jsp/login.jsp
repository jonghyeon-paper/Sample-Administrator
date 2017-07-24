<%@ page contentType="text/html; charset=utf-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
	<title>Login Page</title>
</head>
<body>
	<h3>Login with Username and Password</h3>
	
	<form name="f" action="${pageContext.request.contextPath}/login/process.do" method="post">
	<table>
		<tr>
			<td>User:</td>
			<td><input type="text" name="username" value="superuser"></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td colspan="2"><input name="submit" type="submit" value="Login"/></td>
		</tr>
	</table>
	</form>
	<sec:authorize access="isAuthenticated()">
	<script>
	document.location.href='${pageContext.request.contextPath}/main.do';
	</script>
	</sec:authorize>
</body>
</html>