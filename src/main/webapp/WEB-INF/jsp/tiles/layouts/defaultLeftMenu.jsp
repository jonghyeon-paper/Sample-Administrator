<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

<sec:authentication property="principal.customUserData.accessibleMenu" var="accessibleMenu" />

	<nav class="navbar navbar-default navbar-fixed-side">
		<div>
			&nbsp;
		</div>
		
		<ul class="nav">
			<%-- 맨 위의 메뉴는 더미메뉴로 출력하지 않는다. 바로 자식메뉴를 출력한다. --%>
			<%-- 2단계 메뉴까지만 그린다. ul에 사용되는 클래스가 달라서 재귀호출을 사용할 수 없다. --%>
			<c:forEach var="item" items="${accessibleMenu.childMenu}" varStatus="status">
				<li>
					<a href="${contextPath}/${item.uri}">${item.menuName}</a>
					<c:if test="${item.childMenu ne null and item.childMenu ne ''}">
						<ul class="nav">
							<c:forEach var="item2" items="${item.childMenu}" varStatus="status2">
								<li>
									<a href="${contextPath}/${item2.uri}">${item2.menuName}</a>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</li>
			</c:forEach>
			
			<%-- 
			<li>
				<a href="${contextPath}/user/listview.do">사용자 관리</a>
				<ul class="nav">
					<li>
						<a href="${contextPath}/authority/view.do">권한 관리</a>
					</li>
					<li>
						<a href="${contextPath}/menu/view.do">메뉴 관리</a>
					</li>
					<li>
						<a href="${contextPath}/ticket/listView.do">IT요청현황</a>
						<ul class="nav">
							<li>
								<a href="${contextPath}/authority/view.do">권한 관리</a>
							</li>
							<li>
								<a href="${contextPath}/menu/view.do">메뉴 관리</a>
							</li>
							<li>
								<a href="${contextPath}/ticket/listView.do">IT요청현황</a>
							</li>
						</ul>
					</li>
				</ul>
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
			<li>
				<a href="${contextPath}/ticket/listView.do">IT요청현황</a>
			</li>
			<li>
				<a href="#">단말기관리</a>
			</li>
			<li>
				<a href="#">통신사관리</a>
			</li>
			 --%>
		</ul>
	</nav>