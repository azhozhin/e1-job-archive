package ru.xrm.app;

public class VacancySectionProperty {

	private String key;
	private String cssQuery;
	
	public VacancySectionProperty(String key, String cssQuString) {
		this.key = key;
		this.cssQuery = cssQuString;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCssQuery() {
		return cssQuery;
	}

	public void setCssQuery(String cssQuery) {
		this.cssQuery = cssQuery;
	}
	
	
}
