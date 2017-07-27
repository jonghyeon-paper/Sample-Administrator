<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<p>file upload/download sample page!</p>
	
	<p>upload sample file : /src/main/java/com/skplanet/iba/support/excel/sample/Book1.xlsx</p>

	<div>
	<form id="fileForm" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<td><label for="file1">파일 첫 번째</label></td>
				<td><input type="file" name="file1"></td>
			</tr>
			<tr>
				<td><label for="file2">파일 두 번째</label></td>
				<td><input type="file" name="file2"></td>
			</tr>
			<tr>
				<td><label for="file1">파일 세 번째</label></td>
				<td><input type="file" name="file3"></td>
			</tr>
			<tr>
				<td colspan="2"><button id="fileUpload">파일 업로드</button></td>
			</tr>
		</table>
	</form>
	</div>
	
	<br><br><br><br><br>
	
	<div>
		<table>
			<tr>
				<td>
					xls 파일 : 
					<button id="fileDownload" onclick="location.href='${pageContext.request.contextPath}/file/sample/downloadXls.do';return false;">파일 다운로드</button>
				</td>
			</tr>
			<tr>
				<td>
					xlss 파일 : 
					<button id="fileDownload" onclick="location.href='${pageContext.request.contextPath}/file/sample/downloadXlsx.do';return false;">파일 다운로드</button>
				</td>
			</tr>
		</table>
	</div>

	<script type="text/javascript">
	$(function(){
		$("#fileUpload").on("click", function(event){
			event.preventDefault();
			var form = new FormData(document.getElementById('fileForm'));
			$.ajax({
				url: '${pageContext.request.contextPath}/file/sample/upload.do',
				data: form,
				dataType: 'json',
				processData: false,
				contentType: false,
				type: 'post',
				success: function (response){
					alert("success!\nconsole data check!");
					console.log(response);
				},
				error: function (jqXHR) {
					alert(jqXHR.responseText);
				}
			});
		});
	});
	</script>