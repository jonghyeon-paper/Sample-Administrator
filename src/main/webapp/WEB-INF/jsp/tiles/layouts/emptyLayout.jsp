<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>
<%@ taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>SK Planet IBAS</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/custom.css">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/js/jquery/jquery-ui-1.12.1.custom/jquery-ui.min.css">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/bootstrap-theme.css">	
	
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery-form/jquery.form.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/common/IbaUtil.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/common/PageUtil-0.0.1.js"></script>
</head>
<body>
<tiles:insertAttribute name="body" />
</body>
</html>
