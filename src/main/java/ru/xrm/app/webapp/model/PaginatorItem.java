package ru.xrm.app.webapp.model;

public class PaginatorItem {
	
	private Long sectionId;
	private Long pageNum;
	
	public PaginatorItem(Long sectionId, Long pageNum){
		this.sectionId=sectionId;
		this.pageNum=pageNum;
	}
	
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getPageNum() {
		return pageNum;
	}
	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
	}
	
	

}
