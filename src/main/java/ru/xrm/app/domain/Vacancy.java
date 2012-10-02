package ru.xrm.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Vacancy extends DomainObject {

	// All fields should be protected for DomainObject methods
	@Id
	@Column
	protected Long id;

	@Column
	protected Integer salary;

	@Column(name = "job_title")
	protected String jobTitle;

	@Column(name = "duty_type")
	protected String dutyType;

	@Column
	protected String education;

	@Column
	protected String experience;

	@Column
	protected String schedule;

	@ManyToOne
	@JoinColumn(name = "section_id")
	protected Section section;

	@Column
	protected String city;

	// TODO: make employer as separate class
	@Column
	protected String employer;

	@Column(name = "vacancy_date")
	@Type(type = "date")
	protected Date date;

	@Column(name = "contact_information")
	protected String contactInformation;

	@Column(name = "presented_by")
	protected String presentedBy;

	@Column
	protected String body;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
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

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
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
