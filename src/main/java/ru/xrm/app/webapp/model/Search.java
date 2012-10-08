package ru.xrm.app.webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.TypedValue;

import ru.xrm.app.dao.DAOFactory;
import ru.xrm.app.dao.SectionDAO;
import ru.xrm.app.dao.hibernate.impl.SectionDAOHibernateImpl;
import ru.xrm.app.domain.City;
import ru.xrm.app.domain.DutyType;
import ru.xrm.app.domain.Education;
import ru.xrm.app.domain.Employer;
import ru.xrm.app.domain.Schedule;
import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.util.DAOUtil;
import ru.xrm.app.util.HibernateUtil;

@ManagedBean(name="session")
@SessionScoped
public class Search implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final long PERPAGE = 10L; 

	private HtmlInputText simpleSearchString;
	private String currentSimpleSearch;
	
	private List<SectionHolder> sectionHolders;
	private List<Vacancy> currentSearchVacancies;
	private Long currentSectionId=-1L;
	private Long totalPages=-1L;
	private Long currentPage=-1L;
	private List<PaginatorItem> pages=new ArrayList<PaginatorItem>();

	private List<City> cities;
	private List<Schedule> schedules;
	private List<Integer> experiences;
	private List<Education> educations;
	private List<DutyType> dutyTypes;
	private List<Employer> employers;
	private List<Section> sections;
	
	// selected properties
	private String selectedSchedule;
	private String selectedCity;
	private String selectedExperience;
	private String selectedEducation;
	private String selectedDutyType;
	private String selectedEmployer;
	private String selectedSections;
	
	public Search(){
		init();
	}
	
	private void init(){
		currentSimpleSearch="";
		sectionHolders=new ArrayList<SectionHolder>();
		DAOUtil.getInstance().beginTransaction();

		// Load sections and vacancies count per section
		sections = DAOUtil.getInstance().getSectionDAO().findAll();
		
		for (Section s:sections){

			Long vacanciesCount=DAOUtil.getInstance().getVacancyDAO().countByCategoryId(s.getId());
			
			sectionHolders.add(new SectionHolder(s, vacanciesCount));
		}
		
		// Load cities
		cities=new ArrayList<City>();
		cities=DAOUtil.getInstance().getCityDAO().findAll();
		
		// Load schedules
		schedules=new ArrayList<Schedule>();
		schedules=DAOUtil.getInstance().getScheduleDAO().findAll();
		
		// populate experiences
		experiences = new ArrayList<Integer>();
		for (int i=0;i<10;i++){
			experiences.add(i);
		}
		
		// load educations
		educations = new ArrayList<Education>();
		educations = DAOUtil.getInstance().getEducationDAO().findAll();
		
		// load dutyTypes
		dutyTypes = new ArrayList<DutyType>();
		dutyTypes = DAOUtil.getInstance().getDutyTypeDAO().findAll();
		
		// load employers
		employers = new ArrayList<Employer>();
		employers = DAOUtil.getInstance().getEmployerDAO().findAll();

		DAOUtil.getInstance().commitTransaction();
	}

	public HtmlInputText getSimpleSearchString() {
		return simpleSearchString;
	}

	public void setSimpleSearchString(HtmlInputText simpleSearchString) {
		this.simpleSearchString = simpleSearchString;
	}

	public List<SectionHolder> getSectionHolders() {
		return sectionHolders;
	}

	public void setSectionHolders(List<SectionHolder> sectionHolders) {
		this.sectionHolders = sectionHolders;
	}

	public List<Vacancy> getCurrentSearchVacancies() {
		return currentSearchVacancies;
	}

	public void setCurrentSearchVacancies(List<Vacancy> currentSectionVacancies) {
		this.currentSearchVacancies = currentSectionVacancies;
	}

	public Long getCurrentSectionId() {
		return currentSectionId;
	}

	public void setCurrentSectionId(Long currentSectionId) {
		this.currentSectionId = currentSectionId;
	}

	public List<PaginatorItem> getPages() {
		return pages;
	}

	public void setPages(List<PaginatorItem> pages) {
		this.pages = pages;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}
	
	
	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public String getSelectedSchedule() {
		return selectedSchedule;
	}

	public void setSelectedSchedule(String selectedSchedule) {
		this.selectedSchedule = selectedSchedule;
	}

	public String getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(String selectedCity) {
		this.selectedCity = selectedCity;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public List<Integer> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Integer> experiences) {
		this.experiences = experiences;
	}

	public String getSelectedExperience() {
		return selectedExperience;
	}

	public void setSelectedExperience(String selectedExperience) {
		this.selectedExperience = selectedExperience;
	}

	
	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public String getSelectedEducation() {
		return selectedEducation;
	}

	public void setSelectedEducation(String selectedEducation) {
		this.selectedEducation = selectedEducation;
	}
	
	public List<DutyType> getDutyTypes() {
		return dutyTypes;
	}

	public void setDutyTypes(List<DutyType> dutyTypes) {
		this.dutyTypes = dutyTypes;
	}

	public String getSelectedDutyType() {
		return selectedDutyType;
	}

	public void setSelectedDutyType(String selectedDutyType) {
		this.selectedDutyType = selectedDutyType;
	}
	
	public List<Employer> getEmployers() {
		return employers;
	}

	public void setEmployers(List<Employer> employers) {
		this.employers = employers;
	}

	public String getSelectedEmployer() {
		return selectedEmployer;
	}

	public void setSelectedEmployer(String selectedEmployer) {
		this.selectedEmployer = selectedEmployer;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public String getSelectedSections() {
		return selectedSections;
	}

	public void setSelectedSections(String selectedSections) {
		this.selectedSections = selectedSections;
	}

	// web actions
	public String doShowSimpleSearchResults(Long page){
		DAOUtil.getInstance().beginTransaction();

		String searchString=(String)simpleSearchString.getValue();
		
		Criterion restriction=Restrictions.ilike("jobTitle", "%"+searchString+"%");
		
		if (!currentSimpleSearch.equals(searchString)){
			currentSimpleSearch=searchString;
			Long totalVacancies = DAOUtil.getInstance().getVacancyDAO().countByCriterions(restriction);
			
			if (totalVacancies % PERPAGE == 0){
				totalPages = totalVacancies/PERPAGE;
			}else{
				totalPages = totalVacancies/PERPAGE+1;
			}
		}
		
		currentPage = page;
		
		pages.clear();
		Long left = Math.max(0, currentPage-5);
		Long right = Math.min(currentPage+5, totalPages);
		if (right-left<10){
				right = Math.min(right+(10-right+left), totalPages);
				left = Math.max(0, left-(10-right+left));
		}

		for (Long i=left;i<right;i++){
			pages.add(new PaginatorItem(Long.valueOf(currentSimpleSearch.hashCode()),i));
		}
		
		currentSearchVacancies = DAOUtil.getInstance().getVacancyDAO().findManyPagination(page*PERPAGE, PERPAGE, restriction);
		
		//simpleSearchString.setValue("processed");
		
		DAOUtil.getInstance().commitTransaction();
		return ""; // stay on same page
	}
	
	public String doShowVacancies(Long sectionId, Long page){

		DAOUtil.getInstance().beginTransaction();

		// this is new section, get count of vacancies in this section and calculate pages
		if (currentSectionId!=sectionId){
			
			Long totalVacanvies=DAOUtil.getInstance().getVacancyDAO().countByCategoryId(sectionId);

			if (totalVacanvies % PERPAGE == 0){
				totalPages=totalVacanvies/PERPAGE;
			}else{
				totalPages=totalVacanvies/PERPAGE+1;
			}
			currentSectionId=sectionId;
		}

		currentPage=page;
		
		pages.clear();
		Long left=Math.max(0, currentPage-5);
		Long right=Math.min(currentPage+5, totalPages);
		if (right-left<10){
				right=Math.min(right+(10-right+left), totalPages);
				left=Math.max(0, left-(10-right+left));
		}

		for (Long i=left;i<right;i++){
			pages.add(new PaginatorItem(sectionId,i));
		}

		// get data with paginationg
		currentSearchVacancies = DAOUtil.getInstance().getVacancyDAO().findByCategoryIdPagination(sectionId, PERPAGE*page, PERPAGE);
		
		DAOUtil.getInstance().commitTransaction();

		return "";
	}

}
