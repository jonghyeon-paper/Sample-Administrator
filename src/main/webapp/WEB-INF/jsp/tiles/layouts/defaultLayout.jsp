<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>
<%@ taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>SK Planet IBAS</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- Common CSS -->
	<link href="${contextPath }/resources/css/ibas.css" rel="stylesheet" type="text/css">
	<link href="${contextPath }/resources/css/ibasstyle.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="${contextPath }/resources/js/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${contextPath }/resources/js/common/IbaUtil.js"></script>
	<script type="text/javascript" src="${contextPath }/resources/js/common/PageUtil-0.0.1.js"></script>
	
	<!--[if IE 7]><link rel="stylesheet" type="text/css" href="/resources/css/styleIE7.css" /><![endif]-->
	<!--[if lte IE 6]><script type="text/javascript"> location.href='/resources/html/errors/ie6notice.htm';</script><![endif]-->
	<!--[if lt IE 9]><script src="/resources/js/html5.js"></script><![endif]-->
</head>
<body>
<tiles:insertAttribute name="top" />
<tiles:insertAttribute name="leftmenu" />
<tiles:insertAttribute name="body" />
<tiles:insertAttribute name="footer" />
</body>
</html>
