<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

<script type="text/javascript" src="${contextPath}/resources/js/common/IbaUtil.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/common/PageUtil.js"></script>

<script>
$(document).ready(function() {
	getList();	
});

function getList(page){
	IbaUtil.requestAjax("GET", '${contextPath}/sample1/list.do', 'page=1&countPerPage=' + IbaUtil.countPerPage, afterGetList);
}

function afterGetList(data){
	var tableHtml = "";
	
	$.each(data.contents, function(index, item){
		tableHtml += createTableData(index, item)
    });
	
	$("#tableBody").html(tableHtml);
	$("#paging").html(drawPage(data.page, data.totalPage));
}

function createTableData(index, item){
	    var tableData = "";
		tableData += "<tr>"
		tableData += "<td class='txt1'>" + item.code + "</td>";
		tableData += "<td class='txt1'>" + item.name + "</td>";
		tableData += "<td class='txt1'>" + item.regDate + "</td>";
		tableData += "<td class='txt1'>" + item.regUserId + "</td>";
		tableData += "<td class='txt1'>" + item.regDate + "</td>";
		tableData += "<td class='txt1'>" + item.regUserId + "</td>";
		tableData += "</tr>"
		return tableData;
}

function drawPage(page, totalPage){
	PageUtil.setTotalPage(totalPage);
	PageUtil.setSkip(page);
	return PageUtil.draw();
}

function go_page(page){
	IbaUtil.requestAjax("GET", '${contextPath}/sample1/list.do', 'page='+page+'&countPerPage=' + IbaUtil.countPerPage, afterGetList);
}
</script>

<article id="mycontent" style="min-height: 649px;">

		<!-- START:MAIN Area -->

		<div>
			<div class="contbox">
				<!-- [s]content -->
				<!-- [s]crash Logger -->

				<!-- [s]IBAS Graph -->
				<div class="ibas_graph_wrap" style="margin-top: 20px;">
					<div style="float: right; margin-top: -40px; margin-right: 10px;">
						<input type="checkbox" value="1" class="check_search">ID <input
							type="checkbox" value="0" class="check_search">Name <input
							type="checkbox" value="2" class="check_search">Email <a
							href="#" style="float: right;" class="search_btn searchbutton"
							value="">Search</a> <input type="text" id="userkeyword"
							name="userkeyword" class="tbox" title=""
							style="width: 200px; margin-top: 3px;">
					</div>
				</div>
				<!-- [e]IBAS Graph -->

				<!-- [s]IBAS Table -->
				<input type="hidden" value="/adm/user" class="paging_url" />
				<div class="ibas_table_wrap" style="margin-top: 10px;">
					<table summary="IBAS" class="tbl_list1">
						<caption style="visibility: hidden; width: 0; height: 0;">Crash
							Logger Table</caption>
						<colgroup>
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 10%">
							<col style="width: 10%">
							<col style="width: 20%">
							<col style="width: 20%">
						</colgroup>
						<tbody id="tableBody" >

						</tbody>
					</table>
					<!-- [e]IBAS Table -->

					<!-- [s]페이지 스타트 -->

					<div id="paging" class="page">
					</div>
					
				</div>
			</div>
		</div>
		<!-- END:MAIN Area -->
	</article>