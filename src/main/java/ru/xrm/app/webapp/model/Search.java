package ru.xrm.app.webapp.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;

import org.hibernate.Session;

import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.misc.HibernateUtil;

@ManagedBean(name="session")
@SessionScoped
public class Search implements Serializable {

	private HtmlInputText searchString;
	private List<Section> sections;
	private List<Vacancy> currentSectionVacancies;
	private Long currentSectionId=-1L;

	public HtmlInputText getSearchString() {
		return searchString;
	}

	public void setSearchString(HtmlInputText searchString) {
		this.searchString = searchString;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	
	public List<Vacancy> getCurrentSectionVacancies() {
		return currentSectionVacancies;
	}

	public void setCurrentSectionVacancies(List<Vacancy> currentSectionVacancies) {
		this.currentSectionVacancies = currentSectionVacancies;
	}

	public Long getCurrentSectionId() {
		return currentSectionId;
	}

	public void setCurrentSectionId(Long currentSectionId) {
		this.currentSectionId = currentSectionId;
	}

	// web actions
	public String doSearch(){
		searchString.setValue("processed");
		return ""; // stay on same page
	}

	public String doLoadSections(){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		sections = session.createQuery("from Section").list();

		session.getTransaction().commit();

		return "";
	}

	public String doShowVacancies(Long sectionId){
		currentSectionId = sectionId;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String query="from Vacancy as v where v.section.id=:id";
		currentSectionVacancies = session.createQuery(query).setParameter("id", sectionId).list();

		session.getTransaction().commit();

		return "";
	}

}
