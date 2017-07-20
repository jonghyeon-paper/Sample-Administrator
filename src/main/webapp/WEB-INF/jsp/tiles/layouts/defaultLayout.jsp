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
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/bootstrap/bootstrap.min.css">
	<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/bootstrap/bootstrap-theme.css">	
	
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/jquery/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/common/IbaUtil.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/common/PageUtil-0.0.1.js"></script>
</head>
<body>

<tiles:insertAttribute name="top"/>

<div class="container-fluid">
	<div class="row">
		<div class="col-lg-2 container-fluid">
			<nav class="navbar navbar-default navbar-fixed-side">
				<!-- normal collapsible navbar markup -->
				<tiles:insertAttribute name="leftmenu"/>
			</nav>
		</div>
		<div class="col-lg-10 container-fluid">
			<!-- your page content -->
			<tiles:insertAttribute name="body"/>
		</div>
	</div>
	<%-- <tiles:insertAttribute name="footer" /> --%>
</div>

</body>
</html>
