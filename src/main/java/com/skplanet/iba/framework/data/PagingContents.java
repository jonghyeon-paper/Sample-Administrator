package com.skplanet.iba.framework.data;

import java.util.ArrayList;
import java.util.List;

public class PagingContents<CONTENT> {
	private int page;
	private int countPerPage;
	private int totalPage;
	private List<CONTENT> contents;
	
	public PagingContents() {
		this.page = 0;
		this.countPerPage = 0;
		this.totalPage = 0;
		this.contents = new ArrayList<CONTENT>();
	}
	
	public PagingContents(int page, int countPerPage, int totalPage, List<CONTENT> contents) {
		this.page = page;
		this.countPerPage = countPerPage;
		this.totalPage = totalPage;
		this.contents = contents;
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

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<CONTENT> getContents() {
		return contents;
	}

	public void setContents(List<CONTENT> contents) {
		this.contents = contents;
	}
}
