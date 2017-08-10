<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp" %>

	<sec:authorize access="isAuthenticated()">
	<script>
	document.location.href='${pageContext.request.contextPath}/main.do';
	</script>
	</sec:authorize>
	
	<div class="container">
		<div class="well text-center" style="width:500px; height:200px; margin:auto; margin-top:20%;">
			<h3>Login with Username and Password</h3>
			
			<form name="f" action="${pageContext.request.contextPath}/login/process.do" method="post">
			<table style="width:100%;">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tr>
					<td>ID</td>
					<td>
						<input type="text" style="width:80%;" id="username" name="username">
					</td>
				</tr>
				<tr>
					<td>Password</td>
					<td>
						<input type="password" style="width:80%;" id="password" name="password">
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2">
						<input id="submit" name="submit" type="submit" value="Login">
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
	$(function(){
		document.getElementById('username').focus();
		
		$('password').on('input', function(event){
			if (e.keyCode == 13) {
				$('#submit').click();
			}
		});
	});
	</script>