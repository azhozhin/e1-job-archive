package ru.xrm.app.webapp.model;

public class PaginatorItem {
	
	private Long sectionId;
	private Integer pageNum;
	
	public PaginatorItem(Long sectionId, Integer pageNum){
		this.sectionId=sectionId;
		this.pageNum=pageNum;
	}
	
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	

}
