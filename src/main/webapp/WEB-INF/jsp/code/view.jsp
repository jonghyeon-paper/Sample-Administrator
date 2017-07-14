<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<article id="mycontent">
		<div class="bgbox_develop">
			<div class="contbox">
				<div class="ibas_graph_wrap">
					<h4 style="margin-top: 0px;">코드 정보</h4>
				</div>
				
				<div class="ibas_table_wrap" style="margin-top: -20px;">
					<div style="float: left; width: 38%; padding: 1px;" id="hierarchyCodeArea"></div>
					<div style="float: left; width: 58%; padding: 1px;" id="codeInfoArea"></div>
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
	var code = (function(){
		var initialize = function() {
			loadHierarchyCode();
			
			$('#buttonArea').on('click', '#add', function(){
				var $targetArea = $('#codeInfoArea');
				var dataObject = $targetArea.find('#codeInfoTable').data('codeInfo');
				var parameters = {
						parentCodeId: dataObject.codeId
				};
				
				loadCodeForm(parameters, 'add');
			});
			
			$('#buttonArea').on('click', '#edit', function(){
				var $targetArea = $('#codeInfoArea');
				var dataObject = $targetArea.find('#codeInfoTable').data('codeInfo');
				
				loadCodeForm(dataObject, 'edit');
			});
			
			$('#buttonArea').on('click', '#save', function(){
				var $targetArea = $('#codeInfoArea');
				var parameters = {
						actionType: $targetArea.find('#actionType').val(),
						codeId: $targetArea.find('#codeId').val(),
						parentCodeId: $targetArea.find('#parentCodeId').val(),
						codeName: $targetArea.find('#codeName').val(),
						description: $targetArea.find('#description').val(),
						useState: $targetArea.find('#useState').val()	
				};
				
				save(parameters);
			});
		};
		
		var loadHierarchyCode = function(parameters) {
			parameters = parameters || {};
			
			IbaUtil.jsonAjax('${contextPath}/code/hierarchy.do', parameters, function(reponse){
				var $targetArea = $('#hierarchyCodeArea').empty();
				
				var $hierarchyCodeObject = drawHierarchyCodeObject(reponse);
				$hierarchyCodeObject.appendTo($targetArea);
				
			});
		}
		
		var drawHierarchyCodeObject = function(data){
			var top = data;
			
			// draw second depth only
			var $anchor = $('<a>').attr({href: '#', onclick: 'return false;'})
			
			var $div = $('<div>').css({width: '100%', 'padding-top': '20px'});
			
			var $topUl = $('<ul>').css({'list-style': 'disc', 'padding-left': '15px'}).appendTo($div);
			var $topLi = $('<li>').css({'padding': '5px 0 5px 0'}).appendTo($topUl);
			$('<span>').append($anchor.clone().attr({id: 'code-top'}).data('code-info', top).html('TOP'))
			           .appendTo($topLi);
			
			var firstDepth = top.childCode;
			for (let firstItem of firstDepth) {
				var $firstDepthUl = $('<ul>').css({'list-style': 'disc', 'padding-left': '15px'}).appendTo($topLi);
				var $firstDepthLi = $('<li>').css({'padding': '5px 0 5px 0'}).appendTo($firstDepthUl);
				$('<span>').append($anchor.clone().attr({id: 'code-' + firstItem.codeId}).data('code-info', firstItem).html(firstItem.codeName))
				           .appendTo($firstDepthLi);
				
				var secondDepth = firstItem.childCode;
				if (secondDepth !== null && secondDepth.length > 0) {
					for (let secondItem of secondDepth) {
						var $secondDepthUl = $('<ul>').css({'list-style': 'disc', 'padding-left': '15px'}).appendTo($firstDepthLi);
						var $secondDepthLi = $('<li>').css({'padding': '5px 0 5px 0'}).appendTo($secondDepthUl);
						$('<span>').append($anchor.clone().attr({id: 'code-' + secondItem.codeId}).data('code-info', secondItem).html(secondItem.codeName))
						           .appendTo($secondDepthLi);
					}
				}
			}
			
			// click event
			$div.on('click', 'a[id^=code-]', function() {
				loadCodeInfo($(this).data('codeInfo'));
			});
			
			return $div;
		};
		
		var drawCodeFormOject = function(data, writeFlag) {
			
			data = data || {};
			writeFlag = writeFlag === true ? true : false;
			
			var $textObject = null;
			if (writeFlag) {
				$textObject = $('<input>').attr({type: 'text'});
			} else {
				$textObject = $('<span>');
			}
			
			var $table = $('<table>').attr({id: 'codeInfoTable'})
			                         .addClass('tbl_write1')
			                         .data('code-info', data);
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '27%'}))
			         .append($('<col>').attr({width: '73%'}));
			
			var $thead = $('<thead>').appendTo($table);
			
			var $tbody = $('<tbody>').appendTo($table);
			
			if (writeFlag) {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('코드')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'codeId', value: data.codeId}))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부모 코드')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'parentCodeId', value: data.parentCodeId}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('코드 이름')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'codeName', value: data.codeName}))
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
					$('<th>').html('사용 여부')
					         .appendTo($tr);
					$('<th>').append($select)
					         .appendTo($tr);
				}());
				
			} else {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('코드')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.codeId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부모 코드')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.parentCodeId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('코드 이름')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.codeName))
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
					$('<th>').html('사용 여부')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.useState))
					         .appendTo($tr);
				}());
			}
			
			// type decision(add or edit)
			$('<input>').attr({type: 'hidden', id: 'actionType', value: data.actionType})
			            .appendTo($table);
			
			return $table;
		};
		
		var loadCodeInfo = function(data) {
			var $targetArea = $('#codeInfoArea').empty();
			var $codeFormObject = drawCodeFormOject(data, false);
			$codeFormObject.appendTo($targetArea);
			
			// button controll
			if (data.codeId === null ||
					data.codeId === undefined ||
					data.codeId === '' ||
					data.codeId === '-1') {
				// 최상위코드는 등록버튼만 노출
				$('#buttonArea').find('#add').show();
				$('#buttonArea').find('#edit, #save').hide();
			} else {
				$('#buttonArea').find('#add, #edit').show();
				$('#buttonArea').find('#save').hide();
			}
		};
		
		var loadCodeForm = function(data, actionType) {
			// load form
			var $targetArea = $('#codeInfoArea').empty();
			var $codeFormObject = drawCodeFormOject(data, true);
			// set type > add or edit url
			$codeFormObject.find('#actionType').val(actionType);
			$codeFormObject.appendTo($targetArea);
			
			// button controll
			$('#buttonArea').find('#save').show();
			$('#buttonArea').find('#add, #edit').hide();
		};
		
		var save = function(parameters) {
			parameters = parameters || {};
			
			// How know url?
			// use hidden field
			var url = null;
			if (parameters.actionType === 'add') {
				// add
				url = '${contextPath}/code/add.do'
			} else if (parameters.actionType === 'edit') {
				// edit
				url = '${contextPath}/code/edit.do'
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
		code.initialize();
	});
	</script>