<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/share/tag.jsp" %>

	<div class="body-contents">
		<p><h2>사용자 관리</h2></p>
		
		<div class="btn-section text-right" id="buttonArea">
			<button type="button" class="btn btn-default" id="add">추가</button>
			<button type="button" class="btn btn-default" id="remove">삭제</button>
		</div>
		
		<div class="text-center" id="userListArea"></div>
		<div class="text-center" id="paginationArea"></div>
	</div>
	
	<script type="text/javascript">
	var user = (function(){
		var initialze = function() {
			loadUserList();
			
			$('#buttonArea').on('click', '#add', function(){
				add();
			});
			
			$('#buttonArea').on('click', '#remove', function(){
				var $targetArea = $('#userListArea');
				var userId = $targetArea.find('input:radio[name=user]:checked').val();
				
				if (userId === null || userId === undefined || userId === '') {
					alert('선택된 사용자가 없습니다.');
					return false;
				} 
				
				var parameters = {
						userId: userId
				};
				
				if (confirm('선택된 사용자를 삭제하시겠습니까?')) {
					remove(parameters);
				}
			});
		};
		
		var loadUserList = function(parameters){
			// paramters에는 검색조건과 페이지 정보가 있어야 한다.
			parameters = parameters || {};
			parameters.page = parameters.page || 1;  // 페이지값이 없으면 기본 1
			parameters.countPerPage = parameters.countPerPage || 15;  // 없으면 기본 15개
			
			share.formAjax(globalContextPath + '/user/page.do', 'post', parameters, function(reponse){
				(function(){
					var $targetArea = $('#userListArea').empty();
					var $userListObject = drawUserListObject(reponse.contents);
					$userListObject.appendTo($targetArea);
				}());
				
				(function(){
					var $targetArea = $('#paginationArea').empty();
					var $pageObject = page.draw(reponse, parameters, loadUserList);
					$pageObject.appendTo($targetArea);
				}());
			});
		};
		
		var drawUserListObject= function(data){
			var $table = $('<table>').addClass('table table-striped table-bordered table-hover');
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '5%'}))
			         .append($('<col>').attr({width: '20%'}))
			         .append($('<col>').attr({width: '20%'}))
			         .append($('<col>').attr({width: '35%'}))
			         .append($('<col>').attr({width: '20%'}))
			
			var $thead = $('<thead>').appendTo($table);
			var $titleTr = $('<tr>').appendTo($thead);
			$titleTr.append($('<th>').html('선택'))
			        .append($('<th>').html('이름'))
			        .append($('<th>').html('사번'))
			        .append($('<th>').html('부서'))
			        .append($('<th>').html('등록일'));
			
			var $tbody = $('<tbody>').appendTo($table);
			
			var userList = data;
			for (let item of userList) {
				var $tr = $('<tr>').data('user-info', item)
				                   .css({cursor: 'pointer'})
				                   .appendTo($tbody);
				
				$('<td>').append($('<input>').attr({type: 'radio', name: 'user', value: item['userId']}))
				         .appendTo($tr);
				$('<td>').html(item.userName)
				         .appendTo($tr);
				$('<td>').html(item.userId)
				         .appendTo($tr);
				$('<td>').html(item.department)
				         .appendTo($tr);
				$('<td>').html(item.regDate)
				         .appendTo($tr);
			}
			
			$table.on('click', 'tbody > tr > td', function() {
				if ($(this).has('input:radio').length === 0) {
					var userInfo = $(this).parents('tr').data('userInfo');
					
					$('<form>').attr({action: globalContextPath + '/user/infoview.do', method: 'get'})
					           .append($('<input>').attr({name: 'userId', value: userInfo.userId}))
					           .appendTo('body')
					           .submit();
				}
			});
			
			return $table;
		};
		
		var add = function() {
			$('<form>').attr({action: globalContextPath + '/user/infoview.do', method: 'get'})
			           .append($('<input>').attr({name: 'userId', value: ''}))
			           .appendTo('body')
			           .submit();
		};
		
		var remove = function(parameters) {
			parameters = parameters || {};
			
			share.jsonAjax(globalContextPath + '/user/remove.do', parameters, function(reponse){
				if (reponse.responseCode === 'SUCCESS') {
					alert(reponse.responseMessage);
					location.reload();
				} else {
					alert(reponse.responseMessage);
				}
			});
		};
		
		return {
			initialze: initialze,
			loadUserList: loadUserList
		};
	}());
	
	$(function(){
		user.initialze();
	});
	</script>