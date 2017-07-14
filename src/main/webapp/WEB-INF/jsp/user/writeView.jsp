<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<article id="mycontent">
		<div class="bgbox_develop">
			<div class="contbox">
				<div class="ibas_graph_wrap">
					<h4 style="margin-top: 0px;">사용자 정보</h4>
				</div>
				
				<div class="ibas_table_wrap" style="margin-top: -20px;">
					<div style="float: left; width: 70%; padding: 1px;" id="userInfoArea">
						<table class="tbl_write1">
							<colgroup>
								<col style="width: 27%">
								<col style="width: 73%">
							</colgroup>
							<tbody>
								<tr>
									<th>사번</th>
									<td class="sort">
										<input id="userId" name="userId" class="tbox1 size7" type="text" value="" readonly>
										<input type="button" value="사용자 검색" id="ilmUserSearch">
									</td>
									
								</tr>
								<tr>
									<th>이름</th>
									<td class="sort"><input id="userName" name="userName" class="tbox1 size7" type="text" value="" readonly></td>
								</tr>
								<tr>
									<th>부서</th>
									<td class="sort"><input id="department" name="department" class="tbox1 size7" type="text" value="" readonly></td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div style="float: left; width: 20%; padding: 1px;" id="authorityListArea"></div>
				</div>
				
				<div class="ibas_table_wrap" style="margin-top: -20px;" id="buttonArea">
					<div class="ibas_btns">
						<span class="btn6">
							<button type="button" id="save">저장</button>
						</span>
						<span class="btn10" style="margin-left: 20px;">
							<button type="button" id="cancel">취소</button>
						</span>
					</div>
				</div>
			</div>
			
		</div>
	</article>
	
	<div style="display: none;" id="ilmUserLayer">
		<div id="ilmUserSearchArea">
			<input type="text" id="hname">
			<input type="button" id="search" value="검색">
		</div>
		
		<div style="width: 580px; height:290px; overflow:auto;" id="ilmUserListArea">
			<table class="tbl_list1">
				<colgroup>
					<col width="10%">
					<col width="30%">
					<col width="30%">
					<col width="30%">
				</colgroup>
				<thead></thead>
				<tbody>
					<tr>
						<th>선택</th>
						<th>이름</th>
						<th>사번</th>
						<th>부서</th>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div id="ilmUserButtonArea">
			<input type="button" id="confirm" value="확인">
			<input type="button" id="cancel" value="취소">
		</div>
	</div>
	
	<script type="text/javascript">
	var userId = '<c:out value="${user.userId}"/>';
	
	var user = (function(){
		var initialize = function() {
			if (userId) {
				loadUserInfo({userId: userId});
			}
			
			$('#userInfoArea').on('click', '#ilmUserSearch', function() {
				// 사용자 검색 레이어 팝업
				$("#ilmUserLayer").dialog({
					title: '사용자 검색',
					modal: true,
					width: '600',
					height: '400'
				});
			});
			
			$('#buttonArea').on('click', '#save', function(){
				var $targetArea = $('#userInfoArea');
				
				var userAuthorityList = [];
				console.log($('#authorityListArea').find('input:checkbox:checked').length);
				$('#authorityListArea').find('input:checkbox:checked').each(function(){
					userAuthorityList.push({
							userId: $targetArea.find('#userId').val(),
							authorityId: $(this).val()
					});
				});

				var parameters = {
						userId: $targetArea.find('#userId').val(),
						userName: $targetArea.find('#userName').val(),
						department: $targetArea.find('#department').val(),
						useState: 'USE',
						userAuthorityList: userAuthorityList
				};
				
				save(parameters);
			});
			
			$('#buttonArea').on('click', '#cancel', function(){
				$('<form>').attr({action: '${contextPath}/user/listview.do', method: 'get'})
				           .appendTo('body')
				           .submit();
			});
		};
		
		var loadUserInfo = function(parameters){
			parameters = parameters || {};
			
			IbaUtil.jsonAjax('${contextPath}/user/info.do', parameters, function(reponse){
				applyUserInfo(reponse);
				applyUserAuthority(reponse);
			});
		};
		
		var applyUserInfo = function(data) {
			var $targetArea = $('#userInfoArea');
			
			$targetArea.find('#userId').val(data.userId);
			$targetArea.find('#userName').val(data.userName);
			$targetArea.find('#department').val(data.department);
		};
		
		var applyUserAuthority = function(data) {
			var userAuthorityList = data.userAuthorityList;
			
			var $targetArea = $('#authorityListArea');
			
			for (let item of userAuthorityList) {
				var userAuthorityId = item.authorityId;
				
				$targetArea.find('input:checkbox').each(function(){
					var authorityId = $(this).val();
					if (userAuthorityId === authorityId) {
						$(this).prop('checked', true);
						return false;
					}
				});
			}
		};
		
		var save = function(parameters) {
			var url = null;
			if (userId === null || userId === undefined || userId === '') {
				// add
				url = '${contextPath}/user/add.do'
			} else {
				// edit
				url = '${contextPath}/user/edit.do'
			}
			
			parameters = parameters || {};
			
			IbaUtil.jsonAjax(url, parameters, function(reponse){
				if (reponse.responseCode === 'SUCCESS') {
					alert(reponse.responseMessage);
				} else {
					alert(reponse.responseMessage);
				}
			});
		};
		
		return {
			initialize: initialize,
			loadUserInfo: loadUserInfo,
			applyUserInfo: applyUserInfo
		};
	}());
	
	var authority = (function(){
		var initialize = function(){
			loadAuthrityList({useState: 'USE'});
		};
		
		var loadAuthrityList = function(parameters) {
			parameters = parameters || {};
			
			IbaUtil.jsonAjax('${contextPath}/authority/list.do', parameters, function(reponse){
				var $targetArea = $('#authorityListArea').empty();
				
				var $authorityListObject = drawAutorityListObject(reponse);
				$authorityListObject.appendTo($targetArea);
			});
			
		};
		
		var drawAutorityListObject = function(data){
			var authorityList = data;
			
			var $div = $('<div>').css({padding: '15px'});
			var $ul = $('<ul>').appendTo($div);
			
			for (let item of authorityList) {
				$('<li>').data('authority-info', item)
				         .append($('<span>').append($('<input>').attr({type: 'checkbox',name: 'roleName', value: item['authorityId']})))
				         .append($('<span>').html('&nbsp;'))
				         .append($('<span>').html(item['authorityName']))
				         .appendTo($ul);
			}
			
			return $div;
		};
		
		return {
			initialize: initialize,
			loadAuthrityList: loadAuthrityList
		};
	}());
	
	var ilmUser = (function(){
		var initialize = function() {
			//loadUserList();
			
			$('#ilmUserSearchArea').on('click', '#search', function(){
				var parameters = {
						hname: $('#ilmUserSearchArea').find('#hname').val()
				};
				
				if (parameters.hname === null ||
						parameters.hname === undefined ||
						parameters.hname === '') {
					alert('이름을 입력해 주세요.');
					return false;
				}
				
				loadUserList(parameters);
			});
			
			$('#ilmUserButtonArea').on('click', '#confirm', function(){
				var ilmUserDataObject = $('#ilmUserListArea').find('input:radio:checked').data('ilmUserInfo');
				
				if (ilmUserDataObject === null ||
					ilmUserDataObject === undefined ||
					ilmUserDataObject === '') {
					alert('선택된 사용자가 없습니다.');
					return false;
				}
				
				var parameters = {
						userId: ilmUserDataObject.empno,
						userName: ilmUserDataObject.hname,
						department: ilmUserDataObject.deptnm,
						useState: 'USE'
				};
				user.applyUserInfo(parameters);
				$("#ilmUserLayer").dialog('close');
			});
			
			$('#ilmUserButtonArea').on('click', '#cancel', function(){
				$("#ilmUserLayer").dialog('close');
			});
		};
		
		var loadUserList = function(parameters){
			parameters = parameters || {};
			
			IbaUtil.jsonAjax('${contextPath}/ilmuser/list.do', parameters, function(reponse){
				var $targetArea = $('#ilmUserListArea').empty();
				
				var $userListObject = drawUserListObject(reponse);
				$userListObject.appendTo($targetArea);
			});
		};
		
		var drawUserListObject= function(data){
			var $table = $('<table>').addClass('tbl_list1');
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '10%'}))
			         .append($('<col>').attr({width: '30%'}))
			         .append($('<col>').attr({width: '30%'}))
			         .append($('<col>').attr({width: '30%'}))
			
			var $thead = $('<thead>').appendTo($table);
			
			var $tbody = $('<tbody>').appendTo($table);
			
			var $titleTr = $('<tr>').appendTo($tbody);
			$titleTr.append($('<th>').html('선택'))
			        .append($('<th>').html('이름'))
			        .append($('<th>').html('사번'))
			        .append($('<th>').html('부서'))
			
			var userList = data;
			for (let item of userList) {
				var $tr = $('<tr>').data('user-info', item)
				                   .appendTo($tbody);
				
				$('<td>').addClass('txt1')
				         .append($('<input>').attr({type: 'radio', name: 'user'}).data('ilm-user-info', item))
				         .appendTo($tr);
				$('<td>').addClass('txt1')
				         .html(item.hname)
				         .appendTo($tr);
				$('<td>').addClass('txt1')
				         .html(item.empno)
				         .appendTo($tr);
				$('<td>').addClass('txt1')
				         .html(item.deptnm)
				         .appendTo($tr);
			}
			
			return $table;
		};
		
		return {
			initialize: initialize
		};
	}());
	
	$(function(){
		authority.initialize();
		ilmUser.initialize();
		user.initialize();
	});
	</script>