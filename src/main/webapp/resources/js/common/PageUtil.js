PageUtil = function(){}

PageUtil.skip 		= 1;
PageUtil.max 		= 10;
PageUtil.disPageCnt = 10;

PageUtil.totalPage	= 1;

PageUtil.StartRows = 1;
PageUtil.EndRows = 10;

PageUtil.setTotalPage= function(totalPage) {
	this.totalPage = totalPage;
};

PageUtil.setSkip= function(skip) {
	this.skip = skip;
};

PageUtil.setMax= function(max) {
	this.max = max;
};

PageUtil.setDisPageCnt= function(disPageCnt) {
	this.disPageCnt = disPageCnt;
};

PageUtil.setStartRows= function(StartRows) {
	this.StartRows = StartRows;
};

PageUtil.setEndRows= function(EndRows) {
	this.EndRows = EndRows;
};

PageUtil.draw = function() {
	
	if(this.totalPage == 0) return "";
	
	var prev		= this.skip > 1 ? this.skip - 1 : 1;
	var next		= this.skip < this.totalPage ? this.skip + 1 : this.totalPage;

	var curPos  	= (parseInt((this.skip/this.disPageCnt))+(this.skip%this.disPageCnt>0 ? 1:0));
	
	var prevPage	= curPos > 1 ?  parseInt((curPos - 1)) * this.disPageCnt : 0;
	var nextPage	= (curPos * this.disPageCnt + 1) <= this.totalPage ? curPos * this.disPageCnt + 1 : 0;

	var start		= ((parseInt((this.skip/this.disPageCnt))+(this.skip%this.disPageCnt>0 ? 1:0)) * this.disPageCnt - (this.disPageCnt-1));
	var end			= ((parseInt((this.skip/this.disPageCnt))+(this.skip%this.disPageCnt>0 ? 1:0)) * this.disPageCnt);
	
	if(end > this.totalPage) end = this.totalPage;

	var sb			= "<div>";

	if(this.skip > 1)
		sb += "<a href='javascript:go_page(1)' title='처음 페이지로 이동'><span>&lt;&lt; 처음</span></a>&nbsp;&nbsp;";
	else
		sb += "";

	if(this.skip > this.disPageCnt) {
		sb += "<a href='javascript:go_page(" + prevPage + ");'><span>&lt; 이전</span>&nbsp;&nbsp;</a>";
	} else {
		sb += "";
	}

	for(var i=start ; i<=end ; i++) {
		
		if(i == this.skip)		
			sb += "<a href='javascript:go_page(" + i + ");' class='paging' style='color: blue;'>" + i + "</a>&nbsp;&nbsp;&nbsp;"
		else
			sb += "<a href='javascript:go_page(" + i + ");' class='paging' >" + i + "</a>&nbsp;&nbsp;&nbsp;" 
	}


	if(this.totalPage > nextPage && nextPage != 0 ) {
		sb += "&nbsp;&nbsp;<a href='javascript:go_page(" + nextPage + ");'><span>다음&gt;</span></a>";
	} else {
		sb += "";
	}
	
	if(this.skip < this.totalPage)
		sb += "&nbsp;&nbsp;<a href='javascript:go_page(" + this.totalPage + ")' title='마지막 페이지로 이동'><span>끝 &gt;&gt;</span></a>";
	else
		sb += "";
	
	sb += "</div>";

	this.StartRows = this.skip - 1;
	this.EndRows = this.disPageCnt * this.skip;
	
	if(this.StartRows == 0){
		this.StartRows = 1;
	}else{
		this.StartRows = this.disPageCnt*this.StartRows + 1;
	}
	
	return sb;
};