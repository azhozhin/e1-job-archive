package ru.xrm.app.webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;

import org.hibernate.Query;
import org.hibernate.Session;

import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.misc.HibernateUtil;

@ManagedBean(name="session")
@SessionScoped
public class Search implements Serializable {

	private static final int PERPAGE=10; 

	private HtmlInputText searchString;
	private List<Section> sections;
	private List<Vacancy> currentSectionVacancies;
	private Long currentSectionId=-1L;
	private Integer totalPages=-1;
	private Integer currentPage=-1;
	private List<PaginatorItem> vacancyPages=new ArrayList<PaginatorItem>();

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

	public List<PaginatorItem> getVacancyPages() {
		return vacancyPages;
	}

	public void setVacancyPages(List<PaginatorItem> vacancyPages) {
		this.vacancyPages = vacancyPages;
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

	public String doShowVacancies(Long sectionId, Integer page){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Query q;

		session.beginTransaction();

		// this is new section, get count of vacancies in this section and calculate pages
		if (currentSectionId!=sectionId){
			q=session.createQuery("select count(v) from Vacancy v where v.section.id=:id");
			q.setParameter("id", sectionId);
			Integer totalVacanvies=((Long)q.iterate().next()).intValue();
			
			if (totalVacanvies % PERPAGE == 0){
				totalPages=totalVacanvies/PERPAGE;
			}else{
				totalPages=totalVacanvies/PERPAGE+1;
			}

			vacancyPages.clear();
			for (int i=0;i<totalPages;i++){
				vacancyPages.add(new PaginatorItem(sectionId,i));
			}

			currentSectionId=sectionId;
		}

		currentPage=page;		

		// get data with paginationg
		q=session.createQuery("from Vacancy as v where v.section.id=:id");
		q.setParameter("id", sectionId);
		q.setFirstResult(PERPAGE*page);
		q.setMaxResults(PERPAGE);
		currentSectionVacancies = q.list();

		session.getTransaction().commit();

		return "";
	}

}
