<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Administrator</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css?v=${version}">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/bootstrap-theme.css?v=${version}">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/custom.css?v=${version}">
	
	<script>
	var globalContextPath = '${contextPath}';
	</script>
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery-3.2.1.min.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery.form.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/bootstrap/bootstrap.min.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/common/IbaUtil.js?v=${version}"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/common/PageUtil-0.0.1.js?v=${version}"></script>
</head>
<body>

<tiles:insertAttribute name="top"/>

<div class="container-fluid">
	<div class="row">
		<div class="col-lg-2 container-fluid">
			<!-- normal collapsible navbar markup -->
			<tiles:insertAttribute name="leftmenu"/>
		</div>
		<div class="col-lg-10 container-fluid">
			<!-- your page content -->
			<tiles:insertAttribute name="body"/>
		</div>
	</div>
	<%-- <tiles:insertAttribute name="footer" /> --%>
	<%-- 
	<p style="margin-left:270px;">uriAccessRole : <c:out value="${uriAccessRole}"/></p>
	<p style="margin-left:270px;">uriAccessMode : <c:out value="${uriAccessMode}"/></p>
	 --%>
</div>

</body>
</html>
