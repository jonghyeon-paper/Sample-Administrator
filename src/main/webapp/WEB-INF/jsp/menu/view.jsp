<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp" %>

	<div class="body-contents">
		<p><h2>메뉴 관리</h2></p>
		
		<div class="row">
			<div class="col-lg-3">
				<div class="panel panel-default admin-panel">
					<div class="panel-heading"><b>메뉴 계층</b></div>
					<div class="panel-body admin-panel-body scroll-auto" id="meuHierarchyArea"></div>
				</div>
			</div>
			
			<div class="col-lg-5">
				<div class="panel panel-default admin-panel">
					<div class="panel-heading"><b>메뉴 정보</b></div>
					<div class="panel-body" id="menuInfoArea"></div>
				</div>
			</div>
			
			<div class="col-lg-4">
				<div class="panel panel-default admin-panel">
					<div class="panel-heading"><b>접근 허가 URI</b></div>
					<div class="panel-body" id="menuDependenceArea"></div>
				</div>
			</div>
		</div>
		
		<div class="btn-section text-center" id="buttonArea">
			<button type="button" class="btn btn-default" style="display:none;" id="add">추가</button>
			<button type="button" class="btn btn-default" style="display:none;" id="edit">수정</button>
			<button type="button" class="btn btn-default" style="display:none;" id="save">저장</button>
		</div>
	</div>
	
	<script type="text/javascript">
	var menu = (function(){
		var initialize =function() {
			loadMenuHierarchy();
			
			$('#buttonArea').on('click', '#add', function(){
				var $targetArea = $('#menuInfoArea');
				var dataObject = $targetArea.find('#menuInfoTable').data('menuInfo');
				
				var parentMenuId = dataObject.menuId ? dataObject.menuId : '';
				loadMenuInfo({parentMenuId: parentMenuId}, true);
			});
			
			$('#buttonArea').on('click', '#edit', function(){
				var $targetArea = $('#menuInfoArea');
				var dataObject = $targetArea.find('#menuInfoTable').data('menuInfo');
				
				loadMenuInfo({menuId: dataObject.menuId}, true);
			});
			
			$('#buttonArea').on('click', '#save', function(){
				var $targetArea = $('#menuInfoArea');
				var dataObject = $targetArea.find('#menuInfoTable').data('menuInfo');
				
				var menuDependenceList = [];
				$('#menuDependenceArea').find('li').each(function(){
					menuDependenceList.push($(this).data('menuDependenceInfo'));
				});
				
				var parameters = {
						actionType: $targetArea.find('#actionType').val(),
						menuId: dataObject.menuId,
						parentMenuId: dataObject.parentMenuId,
						menuName: $targetArea.find('#menuName').val(),
						uri: $targetArea.find('#uri').val(),
						description: $targetArea.find('#description').val(),
						displayOrder: $targetArea.find('#displayOrder').val(),
						useState: $targetArea.find('#useState').val(),
						menuDependenceList: menuDependenceList
				};
				
				save(parameters);
			});
		};
		
		var loadMenuHierarchy = function(parameters) {
			parameters = parameters || {};
			
			IbaUtil.jsonAjax(globalContextPath + '/menu/hierarchy.do', parameters, function(reponse){
				var $targetArea = $('#meuHierarchyArea').empty();
				
				var $menuHierarchyObject = drawMenuHierarchyObject(reponse);
				$menuHierarchyObject.appendTo($targetArea);
				
			});
		}
		
		var drawMenuHierarchyObject = function(data){
			var dummyTop = [data];
			
			var $div = $('<div>');
			
			var $childMenuObject = drawChildMenuObject(dummyTop);
			if ($childMenuObject !== null) {
				$childMenuObject.appendTo($div);
			}
			
			// click event
			$div.on('click', 'a[id^=menu-]', function() {
				var dataObject = $(this).data('menuInfo');
				loadMenuInfo(dataObject);
			});
			
			return $div;
		};
		
		var drawChildMenuObject = function(data) {
			if (data && data.length > 0) {
				var $anchor = $('<a>').attr({href: '#', onclick: 'return false;'});
				
				var $ul = $('<ul>');
				for (let item of data) {
					var $li = $('<li>').appendTo($ul);
					$('<span>').append($anchor.clone().attr({id: 'menu-' + item.menuId})
					                                  .data('menu-info', item)
					                                  .html(item.menuName)
					                  )
					           .appendTo($li);
					
					var $childMenuObject = drawChildMenuObject(item.childMenu);
					if ($childMenuObject !== null) {
						$childMenuObject.appendTo($li);
					}
				}
				return $ul;
			} else {
				return null;
			}
		};
		
		var loadMenuInfo = function(parameters, writeFlag) {
			parameters = parameters || {};
			writeFlag = writeFlag === true ? true : false;
			
			// 추가인 경우(파마티터가 없으면 빈 테이블만 그린다) // TOP이 아니면서
			if (parameters.menuName !== 'TOP' &&
					parameters.menuId === null ||
					parameters.menuId === undefined ||
					parameters.menuId === '') {
				
				(function(){
					// 부모메뉴 ID는 있으므로 테이블을 그리기 위해 데이터는 그대로 넘겨준다.
					var data = parameters;
					var $targetArea = $('#menuInfoArea').empty();
					var $menuInfoObject = drawMenuInfoObject(data, true);
					// action type decision(add or edit)
					$('<input>').attr({type: 'hidden', id: 'actionType', value: 'add'})
					            .appendTo($menuInfoObject);
					$menuInfoObject.appendTo($targetArea);
				}());
				
				(function(){
					var $targetArea = $('#menuDependenceArea').empty();
					var $menuDependenceListbject = drawMenuDependenceListObject([], true);
					$menuDependenceListbject.appendTo($targetArea);
				}());
				
				// button control
				$('#buttonArea').find('#save').show();
				$('#buttonArea').find('#add, #edit').hide();
				
				// 영역을 비우고 종료
				return false;
			}
			
			// 최상위 메뉴인 경우
			if (parameters.menuName === 'TOP') {
				(function(){
					var $targetArea = $('#menuInfoArea').empty();
					var $menuInfoObject = drawMenuInfoObject({menuName: 'TOP', description: '최상위 메뉴', useState:{description: '사용'}});
					$menuInfoObject.appendTo($targetArea);
					
					// action type decision(add or edit)
					$('<input>').attr({type: 'hidden', id: 'actionType', value: 'edit'})
					            .appendTo($menuInfoObject);
				}());
				
				(function(){
					var $targetArea = $('#menuDependenceArea').empty();
					var $menuDependenceListbject = drawMenuDependenceListObject([]);
					$menuDependenceListbject.appendTo($targetArea);
				}());
				
				// button control
				// 최상위 메뉴는 추가버튼만 노출
				$('#buttonArea').find('#add').show();
				$('#buttonArea').find('#save, #edit').hide();
				
				return false;
			}
			
			// 상세 정보 확인(데이터가 있는 경우)
			IbaUtil.jsonAjax(globalContextPath + '/menu/info.do', {menuId: parameters.menuId}, function(reponse){
				(function(){
					var $targetArea = $('#menuInfoArea').empty();
					var $menuInfoObject = drawMenuInfoObject(reponse, writeFlag);
					// action type decision(add or edit)
					$('<input>').attr({type: 'hidden', id: 'actionType', value: 'edit'})
					            .appendTo($menuInfoObject);
					$menuInfoObject.appendTo($targetArea);
				}());
				
				(function(){
					var $targetArea = $('#menuDependenceArea').empty();
					var $menuDependenceListbject = drawMenuDependenceListObject(reponse.menuDependenceList, writeFlag);
					$menuDependenceListbject.appendTo($targetArea);
				}());
				
				// button controll
				if (writeFlag) {
					$('#buttonArea').find('#save').show();
					$('#buttonArea').find('#add, #edit').hide();
				} else {
					$('#buttonArea').find('#add, #edit').show();
					$('#buttonArea').find('#save').hide();
				}
			});
		};
		
		var drawMenuInfoObject = function(data, writeFlag) {
			var $textObject = null;
			if (writeFlag) {
				$textObject = $('<input>').attr({type: 'text'}).addClass('form-control');
			} else {
				$textObject = $('<span>');
			}
			
			var $table = $('<table>').attr({id: 'menuInfoTable'})
			                         .addClass('table table-bordered')
			                         .data('menu-info', data);
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '30%'}))
			         .append($('<col>').attr({width: '70%'}));
			
			var $thead = $('<thead>').appendTo($table);
			var $tbody = $('<tbody>').appendTo($table);
			
			if (writeFlag) {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('메뉴 ID(자동 생성)')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'menuId', value: data.menuId}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부모 메뉴 ID(자동 입력)')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'parentMenuId', value: data.parentMenuId}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('메뉴 이름')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'menuName', value: data.menuName}))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('URI')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'uri', value: data.uri}))
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
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('표시 순서')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'displayOrder', value: data.displayOrder}))
					         .appendTo($tr);
				}());
				
				(function(){
					var $select = $('<select>').attr({id: 'useState'}).addClass('form-control');
					$('<option>').attr({value: 'USE'}).html('사용').appendTo($select);
					$('<option>').attr({value: 'UNUSE'}).html('미사용').appendTo($select);
					
					// apply data in edit mode
					// 메뉴 추가 시 해당 값이 없다.
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
					$('<th>').html('메뉴 ID')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.menuId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부모 메뉴 ID')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.parentMenuId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('메뉴 이름')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.menuName))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('URI')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.uri))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('설명')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.description))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('표시 순서')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.displayOrder))
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
		}
		
		var drawMenuDependenceListObject = function(data, writeFlag) {
			var $div = $('<div>');
			
			var $inputForm = $('<form>').addClass('form-inline')
			                        .appendTo($div);
			
			var $formGroup = $('<div>').addClass('form-group')
			                           .appendTo($inputForm);
			
			$('<label>').attr({'for': 'dependenceUri'})
			            .html('URI : ')
			            .appendTo($formGroup);
			
			$('<input>').attr({type: 'text', id: 'dependenceUri'})
			            .addClass('form-control')
			            .appendTo($formGroup);
			
			$('<button>').attr({type: 'button', id: 'addDependenceUri'})
			             .addClass('btn btn-default')
			             .html('추가')
			             .appendTo($inputForm);
			
			var $ul = $('<ul>').appendTo($div);
			
			// click event
			$div.on('click', '#addDependenceUri', function() {
				var data = {
						dependenceUri: $div.find('#dependenceUri').val()
				};
				
				var $li = $('<li>').data('menu-dependence-info', data)
				                   .appendTo($ul);
				
				$('<span>').css({width: '280px', display: 'inline-block'})
				           .html(data.dependenceUri)
				           .appendTo($li);
				
				if (writeFlag) {
					$('<span>').append($('<button>').attr({type: 'button'})
					                                .addClass('btn btn-default')
					                                .html('삭제')
					                                .on('click', function(){$(this).parents('li').remove();})
					                  )
					           .appendTo($li);
				}
				
				// 값 비우기
				$div.find('#dependenceUri').val('');
			});
			
			// apply data
			var list = data;
			for (let item of list) {
				$div.find('#dependenceUri').val(item.dependenceUri);
				$div.find('#addDependenceUri').click();
			}
			
			if (!writeFlag) {
				$inputForm.remove();
			}
			
			return $div;
		}
		
		var save = function(parameters) {
			parameters = parameters || {};
			
			// How know url? (add or edit?)
			// - The value is in a hidden field in the table.
			// - The value is passed as a parameter.
			// - Parameter name is 'actionType'.
			var url = null;
			if (parameters.actionType === 'add') {
				// add
				url = globalContextPath + '/menu/add.do'
			} else if (parameters.actionType === 'edit') {
				// edit
				url = globalContextPath + '/menu/edit.do'
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
		}
		
		return {
			initialize: initialize
		};
	}());
	
	$(function(){
		menu.initialize();
	});
	</script>