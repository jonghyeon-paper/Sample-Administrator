<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<article id="mycontent">
		<div class="bgbox_develop">
			<div class="contbox">
				<div class="ibas_graph_wrap">
					<h4 style="margin-top: 0px;">권한 정보</h4>
				</div>
				
				<div class="ibas_table_wrap" style="margin-top: -20px;">
					<div style="float: left; width: 38%; padding: 1px;" id="authorityListArea"></div>
					<div style="float: left; width: 58%; padding: 1px;" id="authorityInfoArea"></div>
				</div>
				
				<div class="ibas_table_wrap" style="margin-top: -20px;" id="buttonArea">
					<div class="ibas_btns">
						<span class="btn6">
							<button type="button" style="display:none;" id="save">저장</button>
						</span>
						<span class="btn6" style="margin-left: 20px;">
							<button type="button" style="display:none;" id="add">등록</button>
						</span>
						<span class="btn6" style="margin-left: 20px;">
							<button type="button" style="display:none;" id="edit">수정</button>
						</span>
					</div>
				</div>
			</div>
			
		</div>
	</article>
	
	<script type="text/javascript">
	var authority = (function(){
		var initialize = function() {
			loadAuthorityList();
			
			$('#buttonArea').on('click', '#add', function(){
				loadAuthorityInfo({}, true);
			});
			
			$('#buttonArea').on('click', '#edit', function(){
				var $targetArea = $('#authorityInfoArea');
				var dataObject = $targetArea.find('#authorityInfoTable').data('authorityInfo');
				
				loadAuthorityInfo(dataObject, true);
			});
			
			$('#buttonArea').on('click', '#save', function(){
				var $targetArea = $('#authorityInfoArea');
				var dataObject = $targetArea.find('#authorityInfoTable').data('authorityInfo');
				var parameters = {
						actionType: $targetArea.find('#actionType').val(),
						authorityId: dataObject.authorityId || $targetArea.find('#authorityId').val(),
						authorityName: $targetArea.find('#authorityName').val(),
						description: $targetArea.find('#description').val(),
						useState: $targetArea.find('#useState').val()	
				};
				
				save(parameters);
			});
		};
		
		var loadAuthorityList = function(parameters) {
			parameters = parameters || {};
			
			IbaUtil.jsonAjax('${contextPath}/authority/list.do', parameters, function(reponse){
				var $targetArea = $('#authorityListArea').empty();
				
				var $authorityListObject = drawAuthorityListObject(reponse);
				$authorityListObject.appendTo($targetArea);
				
			});
		}
		
		var drawAuthorityListObject = function(data){
			
			var $div = $('<div>').css({width: '100%', 'padding-top': '20px'});
			
			var $ul = $('<ul>').css({'list-style': 'disc', 'padding-left': '15px'})
			                   .appendTo($div);
			
			var $anchor = $('<a>').attr({href: '#', onclick: 'return false;'});
			
			var list = data;
			for (let item of list) {
				var $li = $('<li>').css({'padding': '5px 0 5px 0'})
				                   .appendTo($ul);
				$('<span>').append($anchor.clone()
				                          .attr({id: 'authrity-' + item.authorityId})
				                          .data('authrity-info', item)
				                          .html(item.authorityName)
				                  )
				           .appendTo($li);
			}
			
			// click event
			$div.on('click', 'a[id^=authrity-]', function() {
				loadAuthorityInfo($(this).data('authrityInfo'));
			});
			
			return $div;
		};
		
		var loadAuthorityInfo = function(data, writeFlag) {
			var $targetArea = $('#authorityInfoArea').empty();
			
			writeFlag =  writeFlag === true ? true : false;
			var $authorityInfoObject = drawAuthorityInfoOject(data, writeFlag);
			$authorityInfoObject.appendTo($targetArea);
			
			// action type decision(add or edit)
			if (data.authorityId !== null &&
					data.authorityId !== undefined &&
					data.authorityId !== '') {
				
				 $('<input>').attr({type: 'hidden', id: 'actionType', value: 'edit'})
				             .appendTo($authorityInfoObject);
			} else {
				 $('<input>').attr({type: 'hidden', id: 'actionType', value: 'add'})
				             .appendTo($authorityInfoObject);
			}
			
			// button controll
			if (writeFlag) {
				$('#buttonArea').find('#save').show();
				$('#buttonArea').find('#edit, #add').hide();
			} else {
				$('#buttonArea').find('#add, #edit').show();
				$('#buttonArea').find('#save').hide();
			}
		};
		
		var drawAuthorityInfoOject = function(data, writeFlag) {
			data = data || {};
			writeFlag = writeFlag === true ? true : false;
			
			var $textObject = null;
			if (writeFlag) {
				$textObject = $('<input>').attr({type: 'text'});
			} else {
				$textObject = $('<span>');
			}
			
			var $table = $('<table>').attr({id: 'authorityInfoTable'})
			                         .addClass('tbl_write1')
			                         .data('authority-info', data);
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '27%'}))
			         .append($('<col>').attr({width: '73%'}));
			
			var $thead = $('<thead>').appendTo($table);
			
			var $tbody = $('<tbody>').appendTo($table);
			
			if (writeFlag) {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('권한 ID')
					         .appendTo($tr);
					
					var $authorityIdInputObject = $textObject.clone().attr({id: 'authorityId'});
					
					if (data.authorityId) {
						$authorityIdInputObject.val(data.authorityId)
						                       .prop('readonly', true);
					}
					
					$('<th>').append($authorityIdInputObject)
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('권한 이름')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'authorityName', value: data.authorityName}))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('설명')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'description', value: data.description}))
					         .appendTo($tr);
				}());
				
				(function(){
					var $select = $('<select>').attr({id: 'useState'});
					$('<option>').attr({value: 'USE'}).html('사용').appendTo($select);
					$('<option>').attr({value: 'UNUSE'}).html('미사용').appendTo($select);
					// apply data in edit mode
					$select.val(data.useState);
					
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('사용 상태')
					         .appendTo($tr);
					$('<th>').append($select)
					         .appendTo($tr);
				}());
				
			} else {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('권한 ID')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.authorityId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('권한 이름')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.authorityName))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('설명')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.description))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('사용 상태')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.useState))
					         .appendTo($tr);
				}());
			}
			
			return $table;
		};
		
		var save = function(parameters) {
			parameters = parameters || {};
			
			// How know url? (add or edit?)
			// - The value is in a hidden field in the table.
			// - Its value is passed as a parameter.
			// -Parameter name is 'actionType'.
			var url = null;
			if (parameters.actionType === 'add') {
				// add
				url = '${contextPath}/authority/add.do'
			} else if (parameters.actionType === 'edit') {
				// edit
				url = '${contextPath}/authority/edit.do'
			}
			
			if (url === null) {
				alert('저장할 수 없습니다.\n관리자에게 문의해주세요.');
				return false;
			}
			
			IbaUtil.jsonAjax(url, parameters, function(reponse){
				if (reponse.responseCode === 'SUCCESS') {
					alert(reponse.responseMessage);
					location.reload();
				} else {
					alert(reponse.responseMessage);
				}
			});
		};
		
		return {
			initialize: initialize
		};
	}());
	
	$(function(){
		authority.initialize();
	});
	</script>