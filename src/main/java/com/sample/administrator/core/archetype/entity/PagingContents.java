package com.sample.administrator.core.archetype.entity;

import java.util.ArrayList;
import java.util.List;

public class PagingContents<CONTENTS> {
	
	private int page;
	private int countPerPage;
	private int totalCount;
	private int totalPage;
	private List<CONTENTS> contents;
	
	public PagingContents() {
		page = 1;
		countPerPage = 15;
		totalCount = 0;
		totalPage = 1;
		contents = new ArrayList<CONTENTS>();
	}
	
	public PagingContents(int page, int countPerPage, List<CONTENTS> contents, int totalCount) {
		this.page = page;
		this.countPerPage = countPerPage;
		this.totalCount = totalCount;
		this.contents = contents;
		calculateTotalPage();
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
		calculateTotalPage();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calculateTotalPage();
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<CONTENTS> getContents() {
		return contents;
	}

	public void setContents(List<CONTENTS> contents) {
		this.contents = contents;
	}
	
	private void calculateTotalPage() {
		totalPage = (this.totalCount / this.countPerPage) + (this.totalCount % this.countPerPage == 0 ? 0 : 1);
	}
}
