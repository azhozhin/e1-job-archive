package ru.xrm.app.webapp.model;

import ru.xrm.app.domain.Section;

public class SectionHolder {

	private Section section;
	private Integer vacanciesCount;
	
	public SectionHolder(Section section, Integer vacanciesCount){
		this.section=section;
		this.vacanciesCount=vacanciesCount;
	}
	
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	public Integer getVacanciesCount() {
		return vacanciesCount;
	}
	public void setVacanciesCount(int vacanciesCount) {
		this.vacanciesCount = vacanciesCount;
	}
	
	
}
