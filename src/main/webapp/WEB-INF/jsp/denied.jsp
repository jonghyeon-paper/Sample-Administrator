<%@ page contentType="text/html; charset=utf-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Access is denied</title>
</head>
<body>
	<h3>접근 권한이 없습니다!</h3>
	<h3>5초후에 메인페이지로 이동합니다.</h3>
	<script>
	setTimeout(function(){
		location.href = '${pageContext.request.contextPath}/main.do';
	}, 5000);
	</script>
</body>
</html>