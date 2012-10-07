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

	private static final int PERPAGE=10; 

	private HtmlInputText searchString;
	private List<SectionHolder> sectionHolders;
	private List<Vacancy> currentSectionVacancies;
	private Long currentSectionId=-1L;
	private Integer totalPages=-1;
	private Integer currentPage=-1;
	private List<PaginatorItem> vacancyPages=new ArrayList<PaginatorItem>();

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
		sectionHolders=new ArrayList<SectionHolder>();
		DAOUtil.getInstance().beginTransaction();

		// Load sections and vacancies count per section
		sections = DAOUtil.getInstance().getSectionDAO().findAll(Section.class);
		
		for (Section s:sections){

			Integer vacanciesCount=DAOUtil.getInstance().getVacancyDAO().countByCategoryId(s.getId());
			
			sectionHolders.add(new SectionHolder(s, vacanciesCount));
		}
		
		// Load cities
		cities=new ArrayList<City>();
		cities=DAOUtil.getInstance().getCityDAO().findAll(City.class);
		
		// Load schedules
		schedules=new ArrayList<Schedule>();
		schedules=DAOUtil.getInstance().getScheduleDAO().findAll(Schedule.class);
		
		// populate experiences
		experiences = new ArrayList<Integer>();
		for (int i=0;i<10;i++){
			experiences.add(i);
		}
		
		// load educations
		educations = new ArrayList<Education>();
		educations = DAOUtil.getInstance().getEducationDAO().findAll(Education.class);
		
		// load dutyTypes
		dutyTypes = new ArrayList<DutyType>();
		dutyTypes = DAOUtil.getInstance().getDutyTypeDAO().findAll(DutyType.class);
		
		// load employers
		employers = new ArrayList<Employer>();
		employers = DAOUtil.getInstance().getEmployerDAO().findAll(Employer.class);

		DAOUtil.getInstance().commitTransaction();
	}

	public HtmlInputText getSearchString() {
		return searchString;
	}

	public void setSearchString(HtmlInputText searchString) {
		this.searchString = searchString;
	}

	public List<SectionHolder> getSectionHolders() {
		return sectionHolders;
	}

	public void setSectionHolders(List<SectionHolder> sectionHolders) {
		this.sectionHolders = sectionHolders;
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

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
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
	public String doSearch(){
		searchString.setValue("processed");
		return ""; // stay on same page
	}
	
	public String doShowVacancies(Long sectionId, Integer page){

		DAOUtil.getInstance().beginTransaction();

		// this is new section, get count of vacancies in this section and calculate pages
		if (currentSectionId!=sectionId){
			
			Integer totalVacanvies=DAOUtil.getInstance().getVacancyDAO().countByCategoryId(sectionId);

			if (totalVacanvies % PERPAGE == 0){
				totalPages=totalVacanvies/PERPAGE;
			}else{
				totalPages=totalVacanvies/PERPAGE+1;
			}
			currentSectionId=sectionId;
		}

		currentPage=page;
		
		vacancyPages.clear();
		int left=Math.max(0, currentPage-5);
		int right=Math.min(currentPage+5, totalPages);
		if (right-left<10){
				right=Math.min(right+(10-right+left), totalPages);
				left=Math.max(0, left-(10-right+left));
		}

		for (int i=left;i<right;i++){
			vacancyPages.add(new PaginatorItem(sectionId,i));
		}

		// get data with paginationg
		currentSectionVacancies = DAOUtil.getInstance().getVacancyDAO().findByCategoryIdPagination(sectionId, PERPAGE*page, PERPAGE);
		
		DAOUtil.getInstance().commitTransaction();

		return "";
	}

}
