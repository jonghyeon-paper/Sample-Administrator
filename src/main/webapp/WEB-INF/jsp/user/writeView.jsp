<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<article id="mycontent">
		<div class="bgbox_develop">
			<div class="contbox">
				<div class="ibas_graph_wrap">
					<h4 style="margin-top: 0px;">사용자 정보</h4>
				</div>
				
				<div class="ibas_table_wrap" style="margin-top: -20px;" id="userInfoArea">
					<div style="float: left; width: 70%; padding: 1px;">
						<table class="tbl_write1">
							<colgroup>
								<col style="width: 27%">
								<col style="width: 73%">
							</colgroup>
							<tbody>
								<tr>
									<th>사번</th>
									<td class="sort"><input id="userId" name="userId" class="tbox1 size7" type="text" value="" readonly></td>
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
	
	<script type="text/javascript">
	var userId = '<c:out value="${user.userId}"/>';
	
	var user = (function(){
		var initialize = function() {
			loadUserInfo({userId: userId});
			
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
				cancel();
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
			
			$targetArea .find('#userId').val(data.userId);
			$targetArea .find('#userName').val(data.userName);
			$targetArea .find('#department').val(data.department);
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
		
		var cancel = function() {
			$('<form>').attr({action: '${contextPath}/user/listview.do', method: 'get'})
			           .appendTo('body')
			           .submit();
		};
		
		return {
			initialize: initialize,
			loadUserInfo: loadUserInfo
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
	
	$(function(){
		if (userId === null || userId === undefined || userId === '') {
			// add mode
			authority.initialize();
			
			// 사용자 검색 레이어 팝업
			alert('사용자 검색 기능 미구현');
		} else {
			// edit mode
			authority.initialize();
			user.initialize();
		}
	});
	</script>