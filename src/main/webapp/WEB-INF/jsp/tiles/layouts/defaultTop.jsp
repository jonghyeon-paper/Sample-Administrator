<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<<script>
function go_ticket(){
	window.location.href = '${contextPath}/ticket/listView.do';
}
</script>
	<!-- [s]crash logger title -->
	<div class="ibas_title">
		<div class="ibas_title_detail">
			<p class="ibas_txt1">IBAS - Intranet Backoffice & API System</p>
		</div>
		<div class="ibas_location">
		</div>
	</div>
	<!-- [e]crash logger title -->
	<!-- [s]crash logger header -->
	<header id="ibas_header">
		<ul class="ibas_menu">
			<li class="menu">
				<a href="${contextPath}/user/listview.do" style="color: #fff;">사용자 관리</a>
			</li>
			<li class="menu">
				<a href="${contextPath}/code/view.do" style="color: #fff;">코드 관리</a>
			</li>
			<li class="menu">
				<a href="${contextPath}/authority/view.do" style="color: #fff;">권한 관리</a>
			</li>
			<li class="menu">
				<a href="${contextPath}/menu/view.do" style="color: #fff;">메뉴 관리</a>
			</li>
			<li class="menu" onClick="go_ticket();">IT요청현황</li>
			<li class="menu">단말기관리</li>
			<li class="menu">통신사관리</li>
		</ul>

		<ul class="ibas_mymenu">
			<li>Logged in as <span class="mymenu_user">1003055</span></li>
			<li class="menu" >Log-out</li>
		</ul>
	</header>
