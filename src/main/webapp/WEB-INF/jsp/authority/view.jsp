<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp" %>

	<div class="body-contents">
		<p><h2>권한 관리</h2></p>
	
		<div class="row">
			<div class="col-lg-3">
				<div class="panel panel-default admin-panel">
					<div class="panel-heading"><b>권한 목록</b></div>
					<div class="panel-body admin-panel-body scroll-auto" id="authorityListArea"></div>
				</div>
			</div>
			
			<div class="col-lg-5">
				<div class="panel panel-default admin-panel-half">
					<div class="panel-heading"><b>권한 정보</b></div>
					<div class="panel-body" id="authorityInfoArea"></div>
				</div>
				<div class="panel panel-default admin-panel-half">
					<div class="panel-heading"><b>허가 정보</b></div>
					<div class="panel-body" id="authorityAccessListArea"></div>
				</div>
			</div>
			
			<div class="col-lg-4">
				<div class="panel panel-default admin-panel">
					<div class="panel-heading"><b>접근 허가 메뉴</b></div>
					<div class="panel-body admin-panel-body scroll-auto" id="menuHierarchyArea"></div>
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
	var authority = (function(){
		var initialize = function() {
			loadAuthorityList();
			
			$('#buttonArea').on('click', '#add', function(){
				loadAuthorityInfo();
			});
			
			$('#buttonArea').on('click', '#edit', function(){
				var $targetArea = $('#authorityInfoArea');
				var dataObject = $targetArea.find('#authorityInfoTable').data('authorityInfo');
				
				loadAuthorityInfo(dataObject, true);
			});
			
			$('#buttonArea').on('click', '#save', function(){
				var $targetArea = $('#authorityInfoArea');
				var dataObject = $targetArea.find('#authorityInfoTable').data('authorityInfo');
				
				var authorityAccessList = [];
				$('#authorityAccessListArea').find('input:checkbox:checked').each(function(){
					authorityAccessList.push({
							authorityId: dataObject.authorityId || $targetArea.find('#authorityId').val(),
							accessMode: $(this).val()
					});
				});
				
				var authorityMenuList = [];
				$('#menuHierarchyArea').find('input:checkbox:checked').each(function(){
					authorityMenuList.push({
							authorityId: dataObject.authorityId || $targetArea.find('#authorityId').val(),
							menuId: $(this).val()
					});
				});
				
				var parameters = {
						actionType: $targetArea.find('#actionType').val(),
						authorityId: dataObject.authorityId || $targetArea.find('#authorityId').val(),
						authorityName: $targetArea.find('#authorityName').val(),
						description: $targetArea.find('#description').val(),
						useState: $targetArea.find('#useState').val(),
						authorityAccessList: authorityAccessList,
						authorityMenuList: authorityMenuList
				};
				
				save(parameters);
			});
		};
		
		var loadAuthorityList = function(parameters) {
			parameters = parameters || {};
			
			share.jsonAjax(globalContextPath + '/authority/list.do', parameters, function(reponse){
				var $targetArea = $('#authorityListArea').empty();
				
				var $authorityListObject = drawAuthorityListObject(reponse);
				$authorityListObject.appendTo($targetArea);
				
			});
		}
		
		var drawAuthorityListObject = function(data){
			var $div = $('<div>');
			var $ul = $('<ul>').appendTo($div);
			var $anchor = $('<a>').attr({href: '#', onclick: 'return false;'});
			
			var list = data;
			for (let item of list) {
				var $li = $('<li>').appendTo($ul);
				$('<span>').append($anchor.clone().attr({id: 'authrity-' + item.authorityId})
				                                  .data('authrity-info', item)
				                                  .html(item.authorityName)
				                  )
				           .appendTo($li);
			}
			
			// click event
			$ul.on('click', 'a[id^=authrity-]', function() {
				var dataObject = $(this).data('authrityInfo');
				loadAuthorityInfo(dataObject);
			});
			
			return $div;
		};
		
		var loadAuthorityInfo = function(parameters, writeFlag) {
			parameters = parameters || {};
			writeFlag =  writeFlag === true ? true : false;
			
			// 추가인 경우(파마티터가 없으면 빈 테이블만 그린다)
			if (parameters.authorityId === null ||
					parameters.authorityId === undefined ||
					parameters.authorityId === '') {
				
				(function(){
					var $targetArea = $('#authorityInfoArea').empty();
					var $authorityInfoObject = drawAuthorityInfoOject({}, true);
					$authorityInfoObject.appendTo($targetArea);
					
					// action type decision(add or edit)
					$('<input>').attr({type: 'hidden', id: 'actionType', value: 'add'})
					            .appendTo($authorityInfoObject);
				}());
				
				(function(){
					var $targetArea = $('#authorityAccessListArea').empty();
					var authorityAccessList = authorityAccess.getAuthorityAccessList();
	 				var $authorityAccessListObject = authorityAccess.drawAuthorityAccessListObject(authorityAccessList, true);
	 				$authorityAccessListObject.appendTo($targetArea);
				}());
				
				(function(){
	 				var $targetArea = $('#menuHierarchyArea').empty();
					var menuHierarchy = menu.getMenuHierarchy();
	 				var $menuHierarchyObject = menu.drawMenuHierarchyObject(menuHierarchy, true);
	 				$menuHierarchyObject.appendTo($targetArea);
				}());
				
				// button controll
				$('#buttonArea').find('#save').show();
				$('#buttonArea').find('#edit, #add').hide();
				// 영역을 비우고 종료
				return false;
			}
			
			// 상세 정보 확인(파라미터가 있는 경우)
			share.jsonAjax(globalContextPath + '/authority/info.do', {authorityId: parameters.authorityId}, function(reponse){
				(function(){
					var $targetArea = $('#authorityInfoArea').empty();
					var $authorityInfoObject = drawAuthorityInfoOject(reponse, writeFlag);
					$authorityInfoObject.appendTo($targetArea);
					
					// action type decision(add or edit)
					$('<input>').attr({type: 'hidden', id: 'actionType', value: 'edit'})
					            .appendTo($authorityInfoObject);
				}());
				
				(function(){
					var $targetArea = $('#authorityAccessListArea').empty();
					var authorityAccessList = authorityAccess.getAuthorityAccessList();
	 				var $authorityAccessListObject = authorityAccess.drawAuthorityAccessListObject(authorityAccessList, writeFlag);
	 				$authorityAccessListObject.appendTo($targetArea);
	 				
	 				var authorityAccessList = reponse.authorityAccessList;
	 				for (let item of authorityAccessList) {
	 					var authorityAccessMode = item.accessMode.displayValue;
	 					
	 					$authorityAccessListObject.find('input:checkbox').each(function(){
	 						var accessMode = $(this).val();
	 						if (authorityAccessMode === accessMode) {
	 							$(this).prop('checked', true);
	 							return false;
	 						}
	 					});
	 				}
				}());
				
				(function(){
					var $targetArea = $('#menuHierarchyArea').empty();
					var menuHierarchy = menu.getMenuHierarchy();
	 				var $menuHierarchyObject = menu.drawMenuHierarchyObject(menuHierarchy, writeFlag);
	 				$menuHierarchyObject.appendTo($targetArea);
	 				
	 				var authorityMenuList = reponse.authorityMenuList;
	 	 			for (let item of authorityMenuList) {
	 	 				var authorityMenuId = item.menuId;
	 					
	 	 				$menuHierarchyObject.find('input:checkbox').each(function(){
	 	 					var menuId = parseInt($(this).val(), 10);
	 	 					if (authorityMenuId === menuId) {
	 	 						$(this).prop('checked', true);
	 	 						return false;
	 	 					}
	 	 				});
	 	 			}
				}());
				
				// button controll
				if (writeFlag) {
					$('#buttonArea').find('#save').show();
					$('#buttonArea').find('#edit, #add').hide();
				} else {
					$('#buttonArea').find('#add, #edit').show();
					$('#buttonArea').find('#save').hide();
				}
			});
		};
		
		var drawAuthorityInfoOject = function(data, writeFlag) {
			var $textObject = null;
			if (writeFlag) {
				$textObject = $('<input>').attr({type: 'text'}).addClass('form-control');
			} else {
				$textObject = $('<span>');
			}
			
			var $table = $('<table>').attr({id: 'authorityInfoTable'})
			                         .addClass('table table-bordered')
			                         .data('authority-info', data);
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '30%'}))
			         .append($('<col>').attr({width: '70%'}));
			
			var $thead = $('<thead>').appendTo($table);
			var $tbody = $('<tbody>').appendTo($table);
			
			if (writeFlag) {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('권한 ID')
					         .appendTo($tr);
					
					var $authorityIdInputObject = $textObject.clone().attr({id: 'authorityId', value: data.authorityId});
					if (data.authorityId) {
						$authorityIdInputObject.prop('readonly', true);
					}
					
					$('<td>').append($authorityIdInputObject)
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('권한 이름')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().attr({id: 'authorityName', value: data.authorityName}))
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
					// 권한 추가 시 해당 값이 없다.
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
					$('<th>').html('권한 ID')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.authorityId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('권한 이름')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.authorityName))
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
					$('<th>').html('사용 상태')
					         .appendTo($tr);
					$('<td>').append($textObject.clone().html(data.useState.description))
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
				url = globalContextPath + '/authority/add.do'
			} else if (parameters.actionType === 'edit') {
				// edit
				url = globalContextPath + '/authority/edit.do'
			}
			
			if (url === null) {
				alert('저장할 수 없습니다.\n관리자에게 문의해주세요.');
				return false;
			}
			
			share.jsonAjax(url, parameters, function(reponse){
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
	
	var authorityAccess = (function(){
		var authorityAccessList = null;
		
		var initialize = function(){
			// nothing
		};
		
		var getAuthorityAccessList = function() {
			if (authorityAccessList === null) {
				var parameters = {
				};
				share.ajax(globalContextPath + '/authority/access/list.do', false, 'application/json', 'post', JSON.stringify(parameters), 'json', function(reponse){
					authorityAccessList = reponse;
				});
			}
			return authorityAccessList;
		};
		
		var drawAuthorityAccessListObject = function(data, writeFlag) {
			writeFlag = writeFlag === true ? true : false;
			
			var $div = $('<div>').addClass('checkbox');
			var $checkbox = $('<input>').attr({type: 'checkbox'});
			if (!writeFlag) {
				$div.addClass('disabled');
				$checkbox.prop('disabled', true);
			}
			
			var $ul = $('<ul>').appendTo($div);
			for (let item of data) {
				var $li = $('<li>').appendTo($ul);
				
				$('<label>').append($checkbox.clone().attr({id: 'accessMode-' + item.displayValue, value: item.displayValue})
				                                     .data('authority-access-info', item)
				                   )
				            .append(item.description)
				            .appendTo($li);
			}
			
			return $div;
		};
		
		return {
			initialize: initialize,
			getAuthorityAccessList: getAuthorityAccessList,
			drawAuthorityAccessListObject: drawAuthorityAccessListObject
		};
	}());
	
	var menu = (function(){
		var menuHierarchy = null;
		
		var initialize = function() {
			// nothing
		};
		
		var getMenuHierarchy = function(parameters) {
			if (menuHierarchy === null) {
				parameters = {
						useState: 'USE'
				};
				share.ajax(globalContextPath + '/menu/hierarchy.do', false, 'application/json', 'post', JSON.stringify(parameters), 'json', function(reponse){
					menuHierarchy = reponse;
				});
			}
			return menuHierarchy;
		}
		
		var drawMenuHierarchyObject = function(data, writeFlag){
			var dummyTop = [data];
			writeFlag = writeFlag === true ? true : false;
			
			var $div = $('<div>').addClass('checkbox');
			if (!writeFlag) {
				$div.addClass('disabled');
			}
			
			var $childMenuObject = drawChildMenuObject(dummyTop, writeFlag);
			if ($childMenuObject !== null) {
				$childMenuObject.appendTo($div);
			}
			
			return $div;
		};
		
		var drawChildMenuObject = function(data, writeFlag) {
			writeFlag = writeFlag === true ? true : false;
			
			if (data && data.length > 0) {
				var $checkbox = $('<input>').attr({type: 'checkbox'});
				if (!writeFlag) {
					$checkbox.prop('disabled', true);
				}
				
				var $ul = $('<ul>');
				for (let item of data) {
					var $li = $('<li>').appendTo($ul);
					
					// 최상위 메뉴는 checkbox를 만들지 않는다.
					if (item.menuName === 'TOP') {
						$('<span>').html(item.menuName)
						           .appendTo($li);
					} else {
						$('<label>').append($checkbox.clone().attr({id: 'menu-' + item.menuId, value: item.menuId})
						                                     .data('menu-info', item)
						                   )
						            .append(item.menuName)
						            .appendTo($li);
					}
					
					var $childMenuObject = drawChildMenuObject(item.childMenu, writeFlag);
					if ($childMenuObject !== null) {
						$childMenuObject.appendTo($li);
					}
				}
				return $ul;
			} else {
				return null;
			}
		};
		
		return {
			initialize: initialize,
			getMenuHierarchy: getMenuHierarchy,
			drawMenuHierarchyObject: drawMenuHierarchyObject
		};
	}());
	
	$(function(){
		authority.initialize();
		authorityAccess.initialize();
		menu.initialize();
	});
	</script>