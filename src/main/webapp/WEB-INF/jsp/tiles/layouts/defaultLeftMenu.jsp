<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<ul>
		<li>
			<a href="${contextPath}/user/listview.do">사용자 관리</a>
		</li>
		<li>
			<a href="${contextPath}/code/view.do">코드 관리</a>
		</li>
		<li>
			<a href="${contextPath}/authority/view.do">권한 관리</a>
		</li>
		<li>
			<a href="${contextPath}/menu/view.do">메뉴 관리</a>
		</li>
		<li onClick="go_ticket();">
			<a href="${contextPath}/ticket/listView.do">IT요청현황</a>
		</li>
		<li>단말기관리</li>
		<li>통신사관리</li>
	</ul>