<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

<script type="text/javascript" src="${contextPath }/resources/js/common/PageUtil.js"></script>
<script>
$(document).ready(function() {
	onSearch(1);	
});

function onSearch(page){
	IbaUtil.requestAjax("GET", '${contextPath}/ticket/list.do', 'page=' + page + '&countPerPage=' + IbaUtil.countPerPage, afterOnSearch);
}

function afterOnSearch(data){
	var tableBodyHtml = getTableHeader();
	
	$.each(data.contents, function(index, item){
		tableBodyHtml += createTableList(index, item);
    });
	
	$('#tableBody').html(tableBodyHtml);
	$('#paging').html(drawPage(data.page, data.totalPage));
}

function getTableHeader(){
	var tableThHtml = '';
	tableThHtml += '<tr>';
	tableThHtml += '<th><p style="text-align: center;">신청구분</p></th>';
	tableThHtml += '<th><p style="text-align: center;">제목</p></th>';
	tableThHtml += '<th><p style="text-align: center;">요청자</p></th>';
	tableThHtml += '<th><p style="text-align: center;">요청일</p></th>';
	tableThHtml += '<th><p style="text-align: center;">완료요청일</p></th>';
	tableThHtml += '<th><p style="text-align: center;">상태</p></th>';
	tableThHtml +=	'</tr>';
	return tableThHtml;

}

function createTableList(index, item){
	    var tableData = '';
		tableData += '<tr>'
		tableData += '<td align="center">' + item.issueType + '</td>';
		tableData += '<td align="center"><a href="${contextPath}/ticket/infoView.do">' + item.summary + '</a></td>';		
		tableData += '<td align="center">' + item.creatorName + '</td>';
		tableData += '<td align="center">' + item.createDate + '</td>';
		tableData += '<td align="center">' + item.createDate + '</td>';
		tableData += '<td align="center">' + item.status + '</td>';
		tableData += '</tr>'
		return tableData;
}

function drawPage(page, totalPage){
	PageUtil.setTotalPage(totalPage);
	PageUtil.setSkip(page);
	return PageUtil.draw();
}

function go_page(page){
	IbaUtil.requestAjax("GET", '${contextPath}/ticket/list.do', 'page='+page+'&countPerPage=' + IbaUtil.countPerPage, afterGetList);
}
</script>
<div class="container">
<div style="margin: 20px 20px 20px 20px;">
    <h5>IT요청>IT요청현황</h5>
	<table class="table" style="margin: 10px 10px 10px 10px;">
	<tbody id="tableBody">
	</tbody>
	</table>
</div>
</div>