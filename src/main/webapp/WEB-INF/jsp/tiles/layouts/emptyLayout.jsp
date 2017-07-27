<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>
<%@ taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>SK Planet IBAS</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/custom.css?v=${version}">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/jquery-ui.min.css?v=${version}">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css?v=${version}">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/bootstrap-theme.css?v=${version}">	
	
	<script>
	var globalContextPath = '${contextPath}';
	</script>
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery-3.2.1.min.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery-ui.min.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery.form.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/bootstrap/bootstrap.min.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/common/IbaUtil.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/common/PageUtil-0.0.1.js?v=${version}"></script>
</head>
<body>
<tiles:insertAttribute name="body" />
</body>
</html>
