package com.sample.administrator.core.archetype.entity;

public class PageRequest {
	
	private Integer page;
	private Integer countPerPage;

	public PageRequest() {
	}
	
	public PageRequest(int page, int countPerPage) {
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
	
	public Integer getStartIndex() {
		return (page - 1) * countPerPage;
	}
}
