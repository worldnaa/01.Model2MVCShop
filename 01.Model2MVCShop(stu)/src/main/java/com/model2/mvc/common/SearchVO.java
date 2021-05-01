package com.model2.mvc.common;


public class SearchVO {
	
	private int page;
	String searchCondition; //검색조건
	String searchKeyword;   //검색값
	int pageUnit;           //1 페이지 당 보여지는 레코드 수
	
	public SearchVO(){
	}
	
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	@Override
	public String toString() {
		return "SearchVO [page=" + page + ", searchCondition=" + searchCondition + ", searchKeyword=" + searchKeyword
				+ ", pageUnit=" + pageUnit + "]";
	}
	
	
}