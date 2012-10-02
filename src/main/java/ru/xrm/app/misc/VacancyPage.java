package ru.xrm.app.misc;

import ru.xrm.app.domain.DomainObject;

public class VacancyPage extends DomainObject{

	protected String href;
	
	public VacancyPage(){
		
	}
	
	public VacancyPage(String href){
		this.href=href;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public boolean equals(Object obj) {

		if (this==obj){
			return true;
		}
		
		if (obj==null || !(this.getClass() != obj.getClass())){
			return false;
		}
		VacancyPage vp=(VacancyPage)obj;
		return this.href.equals(vp.getHref());
	}
	
}
