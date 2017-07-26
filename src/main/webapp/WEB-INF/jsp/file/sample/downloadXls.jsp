<%@ page language="java" contentType="application/vnd.ms-excel; charset=euc-kr"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

<!DOCTYPE html>
<html>
	<head>
	<title>Insert title here</title>
	</head>
<body>
<table>
	<c:forEach var="item" items="${data}">
		<tr>
			<td>${item.column1}</td>
			<td>${item.column2}</td>
			<td>${item.column3}</td>
			<td>${item.column4}</td>
			<td>${item.column5}</td>
			<td>${item.column6}</td>
			<td>${item.column7}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>