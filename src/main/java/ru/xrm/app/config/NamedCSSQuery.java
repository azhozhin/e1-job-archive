package ru.xrm.app.config;

public class NamedCSSQuery {
	private String name;
	private String cssQuery;
	private int cssArgsCount; 

	public NamedCSSQuery(String queryName, String cssQuery, int cssArgsCount) {
		this.name = queryName;
		this.cssQuery = cssQuery;
		this.cssArgsCount = cssArgsCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssQuery() {
		return cssQuery;
	}

	public void setCssQuery(String cssQuery) {
		this.cssQuery = cssQuery;
	}

	public int getCssArgsCount() {
		return cssArgsCount;
	}

	public void setCssArgsCount(int cssArgsCount) {
		this.cssArgsCount = cssArgsCount;
	}
	
	
}
