package ru.xrm.app.misc;

import ru.xrm.app.domain.DomainObject;

public class VacancyLink extends DomainObject {

	// All fields should be protected for DomainObject methods
	protected String href;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	
}
