PageUtil = function(){}

PageUtil.draw = function(pagingContents, parameters, loadFunction) {
	
	var pageNumber = pagingContents.page;
	var totalPage = pagingContents.totalPage;
	var blockSize = 10;
	
	var prev = pageNumber - 1 > 0 ? pageNumber - 1 : -1;
	var next = pageNumber + 1 <= totalPage ? pageNumber + 1 : -1;

	var remainder = pageNumber % blockSize === 0 ? blockSize : pageNumber % blockSize; // 10단위 학인
	var start = pageNumber - remainder + 1;
	var end = start + blockSize <= totalPage ? start + blockSize : totalPage;
	
	var $pageObject = $('<div>').addClass('page');
	
	$pageObject.append($('<a href="#" onclick="return false;" class="paging" pageIndex="' + prev + '"> <img src="/resources/img/common/btn_paging_pre.gif" border="0" alt="Previous" /></a>&nbsp;'));
	for (var i = start; i <= end; i++) {
		if (i === pageNumber) {
			$pageObject.append($('<a href="#" onclick="return false;" class="paging" pageIndex="' + i + '" style="color: blue;">' + i + '</a>&nbsp;'));
		} else {
			$pageObject.append($('<a href="#" onclick="return false;" class="paging" pageIndex="' + i + '" >' + i + '</a>&nbsp;')); 
		}
	}
	$pageObject.append($('<a href="#" onclick="return false;" class="paging" pageIndex="' + next + '"> <img src="/resources/img/common/btn_paging_next.gif" border="0" alt="Next" /></a>&nbsp;'));
	
	$pageObject.on('click', 'a', function(){
		var pageNumber = parseInt($(this).attr('pageIndex'), 10);
		if (pageNumber < 1) {
			return false;
		}
		
		if (typeof loadFunction === 'function') {
			parameters.page = pageNumber;
			loadFunction.call(this, parameters);
		}
	});
	
	return $pageObject;
};