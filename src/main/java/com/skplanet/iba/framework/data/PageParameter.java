package com.skplanet.iba.framework.data;

public class PageParameter {
	private int page;
	private int countPerPage;
	
	public PageParameter(int page, int countPerPage) {
		this.page = page;
		this.countPerPage = countPerPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	@Override
	public String toString() {
		return "PageParam [page=" + page + ", countPerPage=" + countPerPage + "]";
	}
}
