page = function(){}

page.draw = function(pagingContents, parameters, loadFunction) {
	
	var pageNumber = pagingContents.page;
	var totalPage = pagingContents.totalPage;
	var blockSize = 10;
	
	var prev = pageNumber - 1 > 0 ? pageNumber - 1 : -1;
	var next = pageNumber + 1 <= totalPage ? pageNumber + 1 : -1;

	var remainder = pageNumber % blockSize === 0 ? blockSize : pageNumber % blockSize; // 10단위 학인
	var start = pageNumber - remainder + 1;
	var end = start + blockSize <= totalPage ? start + blockSize : totalPage;
	
	var $ul = $('<ul>').addClass('pagination');
	
	var $privLi = $('<li>').appendTo($ul);
	$('<a>').attr({href: '#', onclick: 'return false;'})
	        .data('page-index', prev)
	        .html('&lt;')
	        .appendTo($privLi);
	
	for (var i = start; i <= end; i++) {
		var $li = $('<li>').appendTo($ul);
		if (i === pageNumber) {
			$li.addClass('active');
		}
		$('<a>').attr({href: '#', onclick: 'return false;'})
		        .data('page-index', i)
		        .html(i)
		        .appendTo($li);
	}
	
	var $nextLi = $('<li>').appendTo($ul);
	$('<a>').attr({href: '#', onclick: 'return false;'})
	        .data('page-index', next)
	        .html('&gt;')
	        .appendTo($nextLi);
	
	$ul.on('click', 'a', function(){
		var pageNumber = parseInt($(this).data('pageIndex'), 10);
		if (pageNumber < 1) {
			return false;
		}
		
		if (typeof loadFunction === 'function') {
			parameters.page = pageNumber;
			loadFunction.call(this, parameters);
		}
	});
	
	return $ul;
};