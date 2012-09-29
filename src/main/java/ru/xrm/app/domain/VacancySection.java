package ru.xrm.app.domain;

public class VacancySection extends DomainObject{

	// All fields should be protected for DomainObject methods
	protected String name;
	protected String href;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	
	
}
