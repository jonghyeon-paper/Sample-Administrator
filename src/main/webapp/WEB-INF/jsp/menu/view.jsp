<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

	<article id="mycontent">
		<div class="bgbox_develop">
			<div class="contbox">
				<div class="ibas_graph_wrap">
					<h4 style="margin-top: 0px;">메뉴 관리</h4>
				</div>
				
				<div class="ibas_table_wrap" style="margin-top: -20px;">
					<div style="float: left; width: 30%; padding: 1px;" id="meuHierarchyArea"></div>
					<div style="float: left; width: 40%; padding: 1px;" id="menuInfoArea"></div>
					<div style="float: left; width: 25%; padding: 1px;" id="menuDependenceArea"></div>
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
	var menu = (function(){
		var initialize =function() {
			loadMenuHierarchy({});
			
			$('#buttonArea').on('click', '#add', function(){
				var $targetArea = $('#menuInfoArea');
				var dataObject = $targetArea.find('#menuInfoTable').data('menuInfo');
				
				var data = {};
				// 최상위 메뉴의 경우 값이 없음
				if (dataObject !== null &&
						dataObject !== undefined &&
						dataObject !== '') {
					
					data.parentMenuId = dataObject.menuId;
				}
				
				loadMenuInput(data);
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
						useState: $targetArea.find('#useState').val(),
						menuDependenceList: menuDependenceList
				};
				
				save(parameters);
			});
		};
		
		var loadMenuHierarchy = function(parameters) {
			parameters = parameters || {};
			
			IbaUtil.jsonAjax('${contextPath}/menu/hierarchy.do', parameters, function(reponse){
				var $targetArea = $('#meuHierarchyArea').empty();
				
				var $menuHierarchyObject = drawMenuHierarchyObject(reponse);
				$menuHierarchyObject.appendTo($targetArea);
				
			});
		}
		
		var drawMenuHierarchyObject = function(data){
			var dummyTop = [data];
			
			var $div = $('<div>').css({width: '100%', 'padding-top': '20px'});
			
			var $childMenuObject = drawChildMenuObject(dummyTop);
			if ($childMenuObject !== null) {
				$childMenuObject.appendTo($div);
			}
			
			// click event
			$div.on('click', 'a[id^=menu-]', function() {
				loadMenuInfo($(this).data('menuInfo'));
			});
			
			return $div;
		};
		
		var drawChildMenuObject = function(data) {
			if (data && data.length > 0) {
				var $anchor = $('<a>').attr({href: '#', onclick: 'return false;'});
				
				var $ul = $('<ul>').css({'list-style': 'disc', 'padding-left': '15px'});
				for (let item of data) {
					var $li = $('<li>').css({'padding': '5px 0 5px 0'}).appendTo($ul);
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
			
			// 최상위 메뉴는 더미값
			// 최상위 메뉴는 등록버튼만 노출
			if (parameters.menuId === null ||
					parameters.menuId === undefined ||
					parameters.menuId === '' ||
					parameters.menuId === -1) {
				
				$('#menuInfoArea').empty();
				$('#menuDependenceArea').empty();
				
				$('#buttonArea').find('#add').show();
				$('#buttonArea').find('#save, #edit').hide();
				// 영역을 비우고 종료
				return false;
			}
			
			IbaUtil.jsonAjax('${contextPath}/menu/info.do', parameters, function(reponse){
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
		
		var loadMenuInput = function(data) {
			data = data || {};
			
			(function(){
				var $targetArea = $('#menuInfoArea').empty();
				var $menuInfoObject = drawMenuInfoObject(data, true);
				$menuInfoObject.appendTo($targetArea);
				
				// action type decision(add or edit)
				$('<input>').attr({type: 'hidden', id: 'actionType', value: 'add'})
				            .appendTo($menuInfoObject);
			}());
			
			(function(){
				var $targetArea = $('#menuDependenceArea').empty();
				var $menuDependenceListbject = drawMenuDependenceListObject([], true);
				$menuDependenceListbject.appendTo($targetArea);
			}());
			
			// button controll
			$('#buttonArea').find('#save').show();
			$('#buttonArea').find('#add, #edit').hide();
		}
		
		var drawMenuInfoObject = function(data, writeFlag) {
			var $textObject = null;
			if (writeFlag) {
				$textObject = $('<input>').attr({type: 'text'});
			} else {
				$textObject = $('<span>');
			}
			
			var $table = $('<table>').attr({id: 'menuInfoTable'})
			                         .addClass('tbl_write1')
			                         .data('menu-info', data);
			
			var $colgroup = $('<colgroup>').appendTo($table);
			$colgroup.append($('<col>').attr({width: '27%'}))
			         .append($('<col>').attr({width: '73%'}));
			
			var $thead = $('<thead>').appendTo($table);
			
			var $tbody = $('<tbody>').appendTo($table);
			
			if (writeFlag) {
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('메뉴 ID(자동 생성)')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'menuId', value: data.menuId}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부모 메뉴 ID(자동 입력)')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'parentMenuId', value: data.parentMenuId}).prop('readonly', true))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('메뉴 이름')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'menuName', value: data.menuName}))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('URI')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().attr({id: 'uri', value: data.uri}))
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
					$('<th>').html('메뉴 ID')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.menuId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('부모 메뉴 ID')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.parentMenuId))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('메뉴 이름')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.menuName))
					         .appendTo($tr);
				}());
				
				(function(){
					var $tr = $('<tr>').appendTo($tbody);
					$('<th>').html('URI')
					         .appendTo($tr);
					$('<th>').append($textObject.clone().html(data.uri))
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
			
			return $table;
		}
		
		var drawMenuDependenceListObject = function(data, writeFlag) {
			var $div = $('<div>').css({width: '100%', 'padding-top': '20px'});
			
			$inputArea = $('<div>').appendTo($div);
			$('<input>').attr({type: 'text', id: 'dependenceUri'})
			            .appendTo($inputArea);
			$('<button>').attr({type: 'text', id: 'addDependenceUri'})
			             .css({'background-color': 'gray'}) //css떄문에 안보여서 임시로 추가함.
			             .html('추가')
			             .appendTo($inputArea);
			
			var $ul = $('<ul>').css({'list-style': 'disc', 'padding-left': '15px'})
			                   .appendTo($div);
			
			// click event
			$div.on('click', 'button[id=addDependenceUri]', function() {
				var data = {
						dependenceUri: $div.find('#dependenceUri').val()
				};
				
				var $li = $('<li>').css({'padding': '5px 0 5px 0'})
				                   .data('menu-dependence-info', data)
				                   .appendTo($ul);
				
				$('<span>').css({width: '200px'})
				           .html(data.dependenceUri)
				           .appendTo($li);
				
				if (writeFlag) {
					$('<span>').append($('<button>').attr({type: 'button'})
					                                .css({'background-color': 'gray'}) //css떄문에 안보여서 임시로 추가함.
					                                .html('삭제')
					                                .on('click', function(){$(this).parents('li').remove();})
					                  )
					           .appendTo($li);
				}
				
				$div.find('#dependenceUri').val('');
			});
			
			// apply data
			var list = data;
			for (let item of list) {
				$div.find('#dependenceUri').val(item.dependenceUri);
				$div.find('#addDependenceUri').click();
			}
			
			if (!writeFlag) {
				$inputArea.remove();
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
				url = '${contextPath}/menu/add.do'
			} else if (parameters.actionType === 'edit') {
				// edit
				url = '${contextPath}/menu/edit.do'
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