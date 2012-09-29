package ru.xrm.app.domain;

import java.util.Date;

public class Vacancy {
	
	private Integer id;

	private String jobTitle;
	private String dutyType;
	private String education;
	private String experience;
	private String schedule;
	private String kindOfBusiness;
	private String city;
	private String employer;
	private Date date;
	private String contactInformation;
	private String presentedBy;
	private String body;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getDutyType() {
		return dutyType;
	}
	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getKindOfBusiness() {
		return kindOfBusiness;
	}
	public void setKindOfBusiness(String kindOfBusiness) {
		this.kindOfBusiness = kindOfBusiness;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}
	public String getPresentedBy() {
		return presentedBy;
	}
	public void setPresentedBy(String presentedBy) {
		this.presentedBy = presentedBy;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
