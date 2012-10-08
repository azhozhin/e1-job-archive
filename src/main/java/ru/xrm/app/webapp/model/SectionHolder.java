package ru.xrm.app.webapp.model;

import ru.xrm.app.domain.Section;

public class SectionHolder {

	private Section section;
	private Long vacanciesCount;
	
	public SectionHolder(Section section, Long vacanciesCount){
		this.section=section;
		this.vacanciesCount=vacanciesCount;
	}
	
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	public Long getVacanciesCount() {
		return vacanciesCount;
	}
	public void setVacanciesCount(Long vacanciesCount) {
		this.vacanciesCount = vacanciesCount;
	}
	
	
}
