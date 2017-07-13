package com.skplanet.iba.framework.data;

public class PagingRequest {
	private Integer page;
	
	private Integer countPerPage;

	public PagingRequest() {
	}
	
	public PagingRequest(int page, int countPerPage) {
		this.page = page;
		this.countPerPage = countPerPage;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(Integer countPerPage) {
		this.countPerPage = countPerPage;
	}
	
	public int calcOffset() {
		return (page - 1) * countPerPage;
	}
	
	public Integer getStartIndex() {
		return (page - 1) * countPerPage;
	}
}
