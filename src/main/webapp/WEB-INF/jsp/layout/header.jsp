<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/share/tag.jsp" %>

	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header navbar-brand">Administrator</div>
		</div>
		<div class="container-fluid">
			<%-- 
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#">Page 1</a></li>
				<li><a href="#">Page 2</a></li>
				<li><a href="#">Page 3</a></li>
			</ul>
			 --%>
			
			<ul class="nav navbar-nav navbar-right">
				<%-- 
				<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
				 --%>
				<li>
					<a href="${pageContext.request.contextPath}/logout.do"><span class="glyphicon glyphicon-log-in"></span> Logout</a>
				</li>
			</ul>
		</div>
	</nav>