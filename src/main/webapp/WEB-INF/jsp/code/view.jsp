<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<div class="container">
		<div class="contents">
			<div class="row">
				<div class="col-lg-3">
					<h4><b>코드 계층</b></h4>
					<div id="hierarchyCodeArea"></div>
				</div>
				
				<div class="col-lg-9">
					<h4><b>코드 정보</b></h4>
					<div id="codeInfoArea"></div>
				</div>
			</div>
			
			<div class="btn-section text-center" id="buttonArea">
				<button type="button" class="btn btn-default" style="display:none;" id="add">등록</button>
				<button type="button" class="btn btn-default" style="display:none;" id="edit">수정</button>
				<button type="button" class="btn btn-default" style="display:none;" id="save">저장</button>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	var code = (function(){
		var initialize = function() {
			loadCodeHierarchy();
			
			$('#buttonArea').on('click', '#add', function(){
				var $targetArea = $('#codeInfoArea');
				var dataObject = $targetArea.find('#codeInfoTable').data('codeInfo');
				var parentCodeId = dataObject.codeId ? dataObject.codeId : '';
				
				loadCodeInfo({parentCodeId: parentCodeId}, true);
			});
			
			$('#buttonArea').on('click', '#edit', function(){
				var $targetArea = $('#codeInfoArea');
				var dataObject = $targetArea.find('#codeInfoTable').data('codeInfo');
				
				loadCodeInfo(dataObject, true);
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
		
		var loadCodeHierarchy = function(parameters) {
			parameters = parameters || {};
			
			IbaUtil.jsonAjax('${contextPath}/code/hierarchy.do', parameters, function(reponse){
				var $targetArea = $('#hierarchyCodeArea').empty();
				
				var $codeHierarchyObject = drawCodeHierarchyObject(reponse);
				$codeHierarchyObject.appendTo($targetArea);
				
			});
		}
		
		var drawCodeHierarchyObject = function(data){
			var dummyTop = [data];
			
			var $div = $('<div>');
			
			var $childCodeObject = drawChildCodeObject(dummyTop);
			if ($childCodeObject !== null) {
				$childCodeObject.appendTo($div);
			}
			
			// click event
			$div.on('click', 'a[id^=code-]', function() {
				loadCodeInfo($(this).data('codeInfo'));
			});
			
			return $div;
		};
		
		var drawChildCodeObject = function(data) {
			if (data && data.length > 0) {
				var $anchor = $('<a>').attr({href: '#', onclick: 'return false;'});
				
				var $ul = $('<ul>').css({'list-style': 'disc', 'padding-left': '15px'});
				for (let item of data) {
					var $li = $('<li>').css({'padding': '5px 0 5px 0'}).appendTo($ul);
					$('<span>').append($anchor.clone().attr({id: 'code-' + item.codeId})
					                                  .data('code-info', item)
					                                  .html(item.codeName)
					                  )
					           .appendTo($li);
					
					var $childCodeObject = drawChildCodeObject(item.childCode);
					if ($childCodeObject !== null) {
						$childCodeObject.appendTo($li);
					}
				}
				return $ul;
			} else {
				return null;
			}
		};
		
		var loadCodeInfo = function(data, writeFlag) {
			data = data || {};
			writeFlag =  writeFlag === true ? true : false;
			
			// 데이터가 없으면 빈 테이블만 그린다. // TOP이 아니면서
			if (data.codeName !== 'TOP' &&
					data.codeId === null ||
					data.codeId === undefined ||
					data.codeId === '') {
				
				var $targetArea = $('#codeInfoArea').empty();
				var $codeInfoObject = drawCodeInfoOject(data, true);
				$codeInfoObject.appendTo($targetArea);
				
				// action type decision(add or edit)
				$('<input>').attr({type: 'hidden', id: 'actionType', value: 'add'})
				            .appendTo($codeInfoObject);
				
				// button control
				$('#buttonArea').find('#save').show();
				$('#buttonArea').find('#add, #edit').hide();
				
				return false;
			}
			
			// 데이터가 있는 경우
			var $targetArea = $('#codeInfoArea').empty();
			var $codeInfoObject = drawCodeInfoOject(data, writeFlag);
			$codeInfoObject.appendTo($targetArea);
			
			// action type decision(add or edit)
			$('<input>').attr({type: 'hidden', id: 'actionType', value: 'edit'})
			            .appendTo($codeInfoObject);
			
			// button control
			if (writeFlag) {
				$('#buttonArea').find('#save').show();
				$('#buttonArea').find('#add, #edit').hide();
			} else {
				$('#buttonArea').find('#add, #edit').show();
				$('#buttonArea').find('#save').hide();
			}
			
			// 최상위코드는 등록버튼만 노출
			if (data.codeId === null ||
					data.codeId === undefined ||
					data.codeId === '' ||
					data.codeId === -1 ||
					data.codeId === '-1') {
				
				$('#buttonArea').find('#edit').hide();
			}
		};
		
		var drawCodeInfoOject = function(data, writeFlag) {
			var $textObject = null;
			if (writeFlag) {
				$textObject = $('<input>').attr({type: 'text'}).addClass('form-control');
			} else {
				$textObject = $('<span>');
			}
			
			var $table = $('<table>').attr({id: 'codeInfoTable'})
			                         .addClass('table table-bordered')
			                         .data('code-info', data);
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '30%'}))
			         .append($('<col>').attr({width: '70%'}));
			
			var $thead = $('<thead>').appendTo($table);
			var $tbody = $('<tbody>').appendTo($table);
			
			if (writeFlag) {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('코드')
					         .appendTo($tr);
					if (data.codeId !== null &&
							data.codeId !== undefined &&
							data.codeId !== '') {
						
						$('<td>').append($textObject.clone().attr({id: 'codeId', value: data.codeId}).prop('readonly', true))
						         .appendTo($tr);
					} else {
						$('<td>').append($textObject.clone().attr({id: 'codeId', value: data.codeId}))
						         .appendTo($tr);
					}
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부모 코드')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'parentCodeId', value: data.parentCodeId}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('코드 이름')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'codeName', value: data.codeName}))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('설명')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'description', value: data.description}))
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
					$('<th>').html('사용 상태')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.useState.description))
					         .appendTo($tr);
				}());
			}
			
			return $table;
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