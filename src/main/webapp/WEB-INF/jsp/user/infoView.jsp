<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<div class="body-contents">
		<p><h2>사용자 관리</h2></p>
		
		<div class="row">
			<div class="col-lg-9">
				<div class="panel panel-default admin-panel">
					<div class="panel-heading"><b>사용자 정보</b></div>
					<div class="btn-section text-right">
						<button type="button" class="btn btn-default" style="display:none;" id="ilmUserSearch">사용자 검색</button>
					</div>
					<div class="panel-body" id="userInfoArea"></div>
				</div>
			</div>
				
			<div class="col-lg-3">
				<div class="panel panel-default admin-panel">
					<div class="panel-heading"><b>권한 정보</b></div>
					<div class="panel-body" id="authorityListArea"></div>
				</div>
			</div>
		</div>
		
		<div class="btn-section text-center" id="buttonArea">
			<button type="button" class="btn btn-default" id="save">저장</button>
			<button type="button" class="btn btn-default" id="edit">수정</button>
			<button type="button" class="btn btn-default" id="cancel">취소</button>
		</div>
	</div>
	
	<div style="display: none;" id="ilmUserLayer">
		<form class="form-inline text-right" id="ilmUserSearchArea" onsubmit="return false;">
			<div class="form-group">
				<label for="hname">이름 : 	</label>
				<input type="text" class="form-control" id="hname">
			</div>
			<button type="button" class="btn btn-default" id="search">검색</button>
		</form>
		
		<div class="text-center" style="width: 560px; height:272px; overflow:auto;" id="ilmUserListArea"></div>
		
		<div class="text-center" id="ilmUserButtonArea">
			<button type="button" class="btn btn-default" id="confirm">확인</button>
			<button type="button" class="btn btn-default" id="cancel">취소</button>
		</div>
	</div>
	
	<script type="text/javascript">
	var userId = '<c:out value="${user.userId}"/>';
	
	var user = (function(){
		var initialize = function() {
			loadUserInfo({userId: userId});
			
			$('#buttonArea').on('click', '#edit', function(){
				var $targetArea = $('#userInfoArea');
				var dataObject = $targetArea.find('#userInfoTable').data('userInfo');
				
				loadUserInfo({userId: dataObject.userId}, true);
			});
			
			$('#buttonArea').on('click', '#cancel', function(){
				$('<form>').attr({action: '${contextPath}/user/listview.do', method: 'get'})
				           .appendTo('body')
				           .submit();
			});
			
			$('#buttonArea').on('click', '#save', function(){
				var $targetArea = $('#userInfoArea');
				
				var userAuthorityList = [];
				
				$('#authorityListArea').find('input:checkbox:checked').each(function(){
					userAuthorityList.push({
							userId: $targetArea.find('#userId').val(),
							authorityId: $(this).val()
					});
				});
				
				var parameters = {
						actionType: $targetArea.find('#actionType').val(),
						userId: $targetArea.find('#userId').val(),
						userName: $targetArea.find('#userName').val(),
						department: $targetArea.find('#department').val(),
						omsId: $targetArea.find('#omsId').val(),
						useState: $targetArea.find('#useState').val(),
						userAuthorityList: userAuthorityList
				};
				
				if (parameters.userId === null ||
						parameters.userId === undefined ||
						parameters.userId === '') {
					
					alert('사용자 정보가 없습니다.');
					return false;
				}
				
				save(parameters);
			});
		};
		
		var loadUserInfo = function(parameters, writeFlag){
			parameters = parameters || {};
			writeFlag =  writeFlag === true ? true : false;
			
			// 검색 파라미터가 없으면 빈 테이블만 그린다.
			if (parameters.userId === null ||
					parameters.userId === undefined ||
					parameters.userId === '') {
				
				(function(){
					var $targetArea = $('#userInfoArea').empty();
					var $userInfoObject = drawUserInfoOject({}, true);
					$userInfoObject.appendTo($targetArea);
					
					// action type decision(add or edit)
					$('<input>').attr({type: 'hidden', id: 'actionType', value: 'add'})
					            .appendTo($userInfoObject);
				}());
				
				(function(){
	 				var $targetArea = $('#authorityListArea').empty();
					var authorityList = authority.getAuthorityList();
	 				var $authorityListObject = authority.drawAutorityListObject(authorityList, true);
	 				$authorityListObject.appendTo($targetArea);
				}());
				
				// button control
				$('#buttonArea').find('#save, #cancel').show();
				$('#buttonArea').find('#edit').hide();
				
				// search button control
				$('#ilmUserSearch').show();
				return false;
			}
			
			// 파라미터가 있는 경우
			IbaUtil.jsonAjax('${contextPath}/user/info.do', parameters, function(reponse){
				(function(){
					var $targetArea = $('#userInfoArea').empty();
					var $userInfoObject = drawUserInfoOject(reponse, writeFlag);
					$userInfoObject.appendTo($targetArea);
					
					// action type decision(add or edit)
					$('<input>').attr({type: 'hidden', id: 'actionType', value: 'edit'})
					            .appendTo($userInfoObject);
				}());
				
				(function(){
	 				var $targetArea = $('#authorityListArea').empty();
					var authorityList = authority.getAuthorityList();
	 				var $authorityListObject = authority.drawAutorityListObject(authorityList, writeFlag);
	 				$authorityListObject.appendTo($targetArea);
	 				
	 	 			var userAuthorityList = reponse.userAuthorityList;
	 	 			for (let item of userAuthorityList) {
	 	 				var userAuthorityId = item.authorityId;
	 					
	 	 				$authorityListObject.find('input:checkbox').each(function(){
	 	 					var authorityId = $(this).val();
	 	 					if (userAuthorityId === authorityId) {
	 	 						$(this).prop('checked', true);
	 	 						return false;
	 	 					}
	 	 				});
	 	 			}
				}());
				
				// button control
				if (writeFlag) {
					$('#buttonArea').find('#save, #cancel').show();
					$('#buttonArea').find('#edit').hide();
				} else {
					$('#buttonArea').find('#edit').show();
					$('#buttonArea').find('#save, #cancel').hide();
				}
				
				// search button control
				$('#ilmUserSearch').hide();
			});
		};
		
		var drawUserInfoOject = function(data, writeFlag) {
			var $textObject = null;
			if (writeFlag) {
				$textObject = $('<input>').attr({type: 'text'}).addClass('form-control');
			} else {
				$textObject = $('<span>');
			}
			
			var $table = $('<table>').attr({id: 'userInfoTable'})
			                         .addClass('table table-bordered')
			                         .data('user-info', data);
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '30%'}))
			         .append($('<col>').attr({width: '70%'}))
			
			var $thead = $('<thead>').appendTo($table);
			var $tbody = $('<tbody>').appendTo($table);
			
			if (writeFlag) {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('사번')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'userId', value: data.userId}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('이름')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'userName', value: data.userName}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부서')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'department', value: data.department}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('OMS ID')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'omsId', value: data.omsId}))
					         .appendTo($tr);
				}());
				
				(function(){
					var $select = $('<select>').attr({id: 'useState'}).addClass('form-control');
					$('<option>').attr({value: 'USE'}).html('사용').appendTo($select);
					$('<option>').attr({value: 'UNUSE'}).html('미사용').appendTo($select);
					
					// apply data in edit mode
					// 사용자 추가 시 해당 값이 없다.
					if (data.useState && data.useState.displayValue) {
						$select.val(data.useState.displayValue);
					}
					
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('사용 상태')
					         .appendTo($tr);
					$('<td>').append($select)
					         .appendTo($tr);
				}());
			} else {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('사번')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.userId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('이름')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.userName))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부서')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.department))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('OMS ID')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.omsId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('사용 상태')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.useState.description))
					         .appendTo($tr);
				}());
			}
			
			return $table;
		};
		
		var applyUserInfo = function(data) {
			var $targetArea = $('#userInfoArea');
			$targetArea.find('#userId').val(data.userId);
			$targetArea.find('#userName').val(data.userName);
			$targetArea.find('#department').val(data.department);
			$targetArea.find('#useState').val(data.useState);
		};
		
		var save = function(parameters) {
			parameters = parameters || {};
			
			// How know url? (add or edit?)
			// - The value is in a hidden field in the table.
			// - The value is passed as a parameter.
			// - Parameter name is 'actionType'.
			var url = null;
			if (parameters.actionType === 'add') {
				// add
				url = '${contextPath}/user/add.do'
			} else if (parameters.actionType === 'edit') {
				// edit
				url = '${contextPath}/user/edit.do';
			}
			
			
			if (url === null) {
				alert('저장할 수 없습니다.\n관리자에게 문의해주세요.');
				return false;
			}
			
			IbaUtil.jsonAjax(url, parameters, function(reponse){
				if (reponse.responseCode === 'SUCCESS') {
					alert(reponse.responseMessage);
					loadUserInfo({userId: parameters.userId});
				} else {
					alert(reponse.responseMessage);
				}
			});
		};
		
		return {
			initialize: initialize,
			applyUserInfo: applyUserInfo
		};
	}());
	
	var authority = (function(){
		var authorityList = null;
		
		var initialize = function(){
			// nothing
		};
		
		var getAuthorityList = function() {
			if (authorityList === null) {
				var parameters = {
						useState: 'USE'
				};
				IbaUtil.ajax('${contextPath}/authority/list.do', false, 'application/json', 'post', JSON.stringify(parameters), 'json', function(reponse){
					authorityList = reponse;
				});
			}
			return authorityList;
		};
		
		var drawAutorityListObject = function(data, writeFlag){
			var authorityList = data;
			writeFlag = writeFlag === true ? true : false;
			
			var $div = $('<div>').addClass('checkbox');
			var $checkbox = $('<input>').attr({type: 'checkbox'});
			if (!writeFlag) {
				$div.addClass('disabled');
				$checkbox.prop('disabled', true);
			}
			
			var $ul = $('<ul>').appendTo($div);
			for (let item of authorityList) {
				var $li = $('<li>').appendTo($ul);
				
				$('<label>').append($checkbox.clone().attr({id: 'authority-' + item.authorityId, value: item.authorityId})
				                                     .data('authority-info', item)
				                   )
				            .append(item.authorityName)
				            .appendTo($li);
			}
			
			return $div;
		};
		
		return {
			initialize: initialize,
			getAuthorityList: getAuthorityList,
			drawAutorityListObject: drawAutorityListObject
		};
	}());
	
	var ilmUser = (function(){
		var initialize = function() {
			loadIlmUserList({});
			
			// 사용자 검색 레이어 팝업
			$('#ilmUserSearch').on('click', function() {
				$("#ilmUserLayer").dialog({
					title: 'ILM 사용자 검색',
					modal: true,
					width: '600',
					height: '400'
				});
			});
			
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
				
				loadIlmUserList(parameters);
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
		
		var loadIlmUserList = function(parameters){
			parameters = parameters || {};
			
			// 검색 파라미터가 없으면 빈 테이블만 그린다.
			if (parameters.hname === null ||
					parameters.hname === undefined ||
					parameters.hname === '') {
				
				var $targetArea = $('#ilmUserListArea').empty();
				
				var $ilmUserListObject = drawIlmUserListObject([]);
				$ilmUserListObject.appendTo($targetArea);
				return false;
			}
			
			IbaUtil.jsonAjax('${contextPath}/ilmuser/list.do', parameters, function(reponse){
				var $targetArea = $('#ilmUserListArea').empty();
				
				var $ilmUserListObject = drawIlmUserListObject(reponse);
				$ilmUserListObject.appendTo($targetArea);
			});
		};
		
		var drawIlmUserListObject= function(data){
			var $table = $('<table>').addClass('table table-striped table-bordered');
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '10%'}))
			         .append($('<col>').attr({width: '20%'}))
			         .append($('<col>').attr({width: '20%'}))
			         .append($('<col>').attr({width: '50%'}))
			
			var $thead = $('<thead>').appendTo($table);
			var $titleTr = $('<tr>').appendTo($thead);
			$titleTr.append($('<th>').html('선택'))
			        .append($('<th>').html('이름'))
			        .append($('<th>').html('사번'))
			        .append($('<th>').html('부서'))
			
			var $tbody = $('<tbody>').appendTo($table);
			
			var userList = data;
			for (let item of userList) {
				var $tr = $('<tr>').data('user-info', item)
				                   .appendTo($tbody);
				
				$('<td>').append($('<input>').attr({type: 'radio', name: 'user'}).data('ilm-user-info', item))
				         .appendTo($tr);
				$('<td>').html(item.hname)
				         .appendTo($tr);
				$('<td>').html(item.empno)
				         .appendTo($tr);
				$('<td>').html(item.deptnm)
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
		user.initialize();
		
		// 사용자 정보가 없는 경우(사용자 추가 시)에만 초기화 한다.
		if (userId === null ||
				userId === undefined ||
				userId === '') {
			
			ilmUser.initialize();
		}
	});
	</script>