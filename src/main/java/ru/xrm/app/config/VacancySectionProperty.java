package ru.xrm.app.config;

public class VacancySectionProperty {

	private String key;
	private String cssQuery;
	
	public VacancySectionProperty(String key, String cssQuery) {
		this.key = key;
		this.cssQuery = cssQuery;
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
