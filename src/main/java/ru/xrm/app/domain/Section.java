package ru.xrm.app.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Section extends DomainObject {

	// All fields should be protected for DomainObject methods
	@Id
	@Column
	protected Long id;

	@Column
	protected String name;

	@Column
	protected String href;

	@OneToMany(mappedBy = "section")
	protected Set<Vacancy> vacancies;

	public Section() {
		this.name = "";
		this.href = "";
		this.vacancies = new HashSet<Vacancy>();
	}

	public Section(String name) {
		this.name = name;
		this.href = "";
		this.vacancies = new HashSet<Vacancy>();
	}

	public Section(String name, String href) {
		this.name = name;
		this.href = href;
		this.vacancies = new HashSet<Vacancy>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<Vacancy> getVacancies() {
		return vacancies;
	}

	public void setVacancies(Set<Vacancy> vacancies) {
		this.vacancies = vacancies;
	}

	@Override
	public boolean equals(Object o) {
		return name.equals((String) o);
	}

}
