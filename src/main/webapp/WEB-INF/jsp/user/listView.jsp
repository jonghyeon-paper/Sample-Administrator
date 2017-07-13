<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<article id="mycontent" style="min-height: 649px;">
		<!-- START:MAIN Area -->

		<div>
			<div class="contbox">
				<%-- search area
				<div class="ibas_graph_wrap" style="margin-top: 20px;">
					<div style="float: right; margin-top: -40px; margin-right: 10px;">
						<input type="checkbox" value="1" class="check_search">ID
						<input type="checkbox" value="0" class="check_search">Name
						<input type="checkbox" value="2" class="check_search">Email
						<a href="#" style="float: right;" class="search_btn searchbutton" value="">Search</a>
						<input type="text" id="userkeyword" name="userkeyword" class="tbox" title="" style="width: 200px; margin-top: 3px;">
					</div>
				</div>
				 --%>
				
				<div class="ibas_table_wrap" style="margin-top: 10px;" id="userListArea">
					<%-- 
					<table class="tbl_list1">
						<colgroup>
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 10%">
							<col style="width: 10%">
							<col style="width: 20%">
							<col style="width: 20%">
						</colgroup>
						<tbody>
							<tr>
								<th style="cursor: pointer;" sort="1" class="ul_sort">ID</th>
								<th style="cursor: pointer;" sort="2" class="ul_sort">Organization</th>
								<th style="cursor: pointer;" sort="3" class="ul_sort">User Group</th>
								<th style="cursor: pointer;" sort="4" class="ul_sort">Name</th>
								<th style="cursor: pointer;" sort="5" class="ul_sort">E-mail</th>
								<th>Action</th>
							</tr>
							<!-- [s]리스트 반복 부분 -->
							<tr>
								<td class="txt1">pp27957</td>
								<td class="txt1">SK Planet/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">박보라</td>
								<td class="txt1"></td>
								<td class="txt1">
									<a href="/adm/user/619" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/619/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">1003731</td>
								<td class="txt1">QA팀/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">조일상</td>
								<td class="txt1">ixiang@sk.com</td>
								<td class="txt1">
									<a href="/adm/user/618" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/618/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">1000135</td>
								<td class="txt1">Syrup서비스개발팀/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">허재형</td>
								<td class="txt1">huhjh@sk.com</td>
								<td class="txt1">
									<a href="/adm/user/617" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/617/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">1002318</td>
								<td class="txt1">Syrup서비스개발팀/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">조성준</td>
								<td class="txt1">torden@sk.com</td>
								<td class="txt1">
									<a href="/adm/user/616" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/616/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">pp59101</td>
								<td class="txt1">SK Planet/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">강혁진</td>
								<td class="txt1">clarence@partner.sk.com</td>
								<td class="txt1">
									<a href="/adm/user/615" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/615/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">1001052</td>
								<td class="txt1">Data Infrastructure팀/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">박지은</td>
								<td class="txt1">jeun.park@sk.com</td>
								<td class="txt1">
									<a href="/adm/user/614" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/614/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">1003665</td>
								<td class="txt1">SC서비스개발팀/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">장성국</td>
								<td class="txt1">sunggook.jang@sk.com</td>
								<td class="txt1">
									<a href="/adm/user/612" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/612/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">1003285</td>
								<td class="txt1">App개발팀/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">백호근</td>
								<td class="txt1">hokun.baek@sk.com</td>
								<td class="txt1">
									<a href="/adm/user/611" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/611/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">PP74146</td>
								<td class="txt1">SPTek/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">이종태</td>
								<td class="txt1"></td>
								<td class="txt1">
									<a href="/adm/user/610" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/610/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
							<tr>
								<td class="txt1">pp43595</td>
								<td class="txt1">테스트조직/SKP</td>
								<td class="txt1">User</td>
								<td class="txt1">정진희</td>
								<td class="txt1"></td>
								<td class="txt1">
									<a href="/adm/user/609" style="cursor: pointer;" class="hide_btn">Edit</a>
									<span style="padding: 0px 10px;">|</span>
									<a href="/adm/user/609/remove" style="cursor: pointer;" class="remove_btn">Remove</a>
									<span style="padding: 0px 10px;"></span>
								</td>
							</tr>
						</tbody>
					</table>
					 --%>
					
					<%-- 
					<div class="page">
						<a href="#" class="paging" pageIndex="10"> <img src="/resources/img/common/btn_paging_pre.gif" border="0" alt="Previous" /></a>&nbsp;
						<a href="#" class="paging" pageIndex="0" style="color: blue;">1</a>&nbsp;
						<a href="#" class="paging" pageIndex="1">2</a>&nbsp;
						<a href="#" class="paging" pageIndex="2">3</a>&nbsp;
						<a href="#" class="paging" pageIndex="3">4</a>&nbsp;
						<a href="#" class="paging" pageIndex="4">5</a>&nbsp;
						<a href="#" class="paging" pageIndex="5">6</a>&nbsp;
						<a href="#" class="paging" pageIndex="6">7</a>&nbsp;
						<a href="#" class="paging" pageIndex="7">8</a>&nbsp;
						<a href="#" class="paging" pageIndex="8">9</a>&nbsp;
						<a href="#" class="paging" pageIndex="9">10</a>&nbsp;
						<a href="#" class="paging" pageIndex="55"> ...56 </a>&nbsp;
						<a href="#" class="paging" pageIndex="10"> <img src="/resources/img/common/btn_paging_next.gif" border="0" alt="Next" /></a>&nbsp;
					</div>
					 --%>
					
					<%-- 
					<div class="btnbox right">
						<span class="btn1">
							<button type="button" id="excelButton"
								onclick="location.href='/adm/user/0'">
								<img src="/resources/img/common/btn_icon_excel.gif" alt="user icon" />Excel Up</button>
						</span>
						<span class="btn1">
							<button type="button" id="excelButton"
								onclick="location.href='/adm/user/0'">
								<img src="/resources/img/common/btn_icon_excel.gif" alt="user icon" />Excel Down</button>
						</span>
					</div>
					 --%>
				</div>
			</div>
		</div>
	</article>
	
	<script type="text/javascript">
	var user = (function(){
		var loadUserList = function(parameters){
			// paramters에는 검색조건과 페이지 정보가 있어야 한다.
			parameters = parameters || {} 
			parameters.page = parameters.page || 1;  // 페이지값이 없으면 기본 1
			parameters.countPerPage = parameters.countPerPage || 15;  // 로우수가 없으면 기본 15
			
			IbaUtil.formAjax('${contextPath}/user/list.do', 'post', parameters, function(reponse){
				var $targetArea = $('#userListArea').empty();
				alert(1);
				
				var $userListObject = drawUserListArea(reponse.contents);
				$userListObject.appendTo($targetArea);
				
				var $pageObject = PageUtil.draw(reponse, parameters, loadUserList);
				$pageObject.appendTo($targetArea);
			});
		};
		
		var drawUserListArea= function(data){
			var $table = $('<table>').addClass('tbl_list1');
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '20%'}))
			         .append($('<col>').attr({width: '20%'}))
			         .append($('<col>').attr({width: '20%'}))
			         .append($('<col>').attr({width: '20%'}))
			         .append($('<col>').attr({width: '20%'}));
			
			var $thead = $('<thead>').appendTo($table);
			
			var $tbody = $('<tbody>').appendTo($table);
			
			var $titleTr = $('<tr>').appendTo($tbody);
			$titleTr.append($('<th>').html(''))
			        .append($('<th>').html('이름'))
			        .append($('<th>').html('사번'))
			        .append($('<th>').html('부서'))
			        .append($('<th>').html('등록일'));
			
			var userList = data;
			for (let item of userList) {
				var $tr = $('<tr>').data('user-info', item)
				                   .appendTo($tbody);
				
				$('<td>').addClass('txt1')
				         .append($('<input>').attr({type: 'radio', name: 'user', value: item['userId']}))
				         .appendTo($tr);
				$('<td>').addClass('txt1')
				         .html(item['userName'])
				         .appendTo($tr);
				$('<td>').addClass('txt1')
				         .html(item['userId'])
				         .appendTo($tr);
				$('<td>').addClass('txt1')
				         .html('?')
				         .appendTo($tr);
				$('<td>').addClass('txt1')
				         .html('?')
				         .appendTo($tr);
			}
		
			return $table;
		};
		
		return {
			loadUserList: loadUserList
		};
	}());
	
	$(function(){
		user.loadUserList();
	});
	</script>
