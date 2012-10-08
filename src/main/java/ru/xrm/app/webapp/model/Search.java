package ru.xrm.app.webapp.model;

import java.io.Serializable;
import java.lang.Thread.State;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;


import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.xrm.app.config.Config;
import ru.xrm.app.domain.City;
import ru.xrm.app.domain.DutyType;
import ru.xrm.app.domain.Education;
import ru.xrm.app.domain.Employer;
import ru.xrm.app.domain.Schedule;
import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.threads.WholeSiteWorker;
import ru.xrm.app.util.CitySet;
import ru.xrm.app.util.DAOUtil;
import ru.xrm.app.util.DutyTypeSet;
import ru.xrm.app.util.EducationSet;
import ru.xrm.app.util.EmployerSet;
import ru.xrm.app.util.HibernateUtil;
import ru.xrm.app.util.LoadAndSave;
import ru.xrm.app.util.ScheduleSet;
import ru.xrm.app.util.SectionSet;

@ManagedBean(name="search")
@SessionScoped
public class Search implements Serializable {

	protected Logger log = LoggerFactory.getLogger( Search.class );

	private static final long serialVersionUID = 1L;

	private static final long PERPAGE = 10L; 

	private HtmlInputText simpleSearchString;
	private String currentSimpleSearch="";

	private List<SectionHolder> sectionHolders;
	private List<Vacancy> currentSearchVacancies;
	private Long remainingTasks=-1L;
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

	// current properties
	private String currentSalaryFrom="";
	private String currentSalaryTo="";
	private String currentDutyType="";
	private String currentEducation="";
	private String currentExperience="";
	private String currentSchedule="";
	private String currentCity="";
	private String currentEmployer="";
	private List<String> currentSections=new ArrayList<String>();

	// selected properties
	private String selectedSalaryFrom="";
	private String selectedSalaryTo="";
	private	String selectedDutyType="";
	private String selectedEducation="";
	private String selectedExperience="";
	private String selectedSchedule="";
	private String selectedCity="";
	private String selectedEmployer="";
	private List<String> selectedSections;

	private String loadPhase="";
	private String processAndSavePhase="";
	
	public Search(){
		init();
	}

	private void init(){
		Config config=Config.getInstance();
		try{
			config.load("config.xml");
		}catch(Exception e){
			e.printStackTrace();
		}

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
		int index=-1;

		for (int i=0;i<schedules.size();i++){
			if ("любой".equals(schedules.get(i).getName().toLowerCase())){
				index=i;
				break;
			}
		}
		if (index!=-1){
			schedules.remove(index);
		}

		// populate experiences
		experiences = new ArrayList<Integer>();
		for (int i=0;i<10;i++){
			experiences.add(i);
		}

		// load educations
		educations = new ArrayList<Education>();
		educations = DAOUtil.getInstance().getEducationDAO().findAll();
		index=-1;
		for(int i=0;i<educations.size();i++){
			if ("любое".equals(educations.get(i).getName().toLowerCase())){
				index=i;
				break;
			}
		}
		if (index!=-1){
			educations.remove(index);
		}

		// load dutyTypes
		dutyTypes = new ArrayList<DutyType>();
		dutyTypes = DAOUtil.getInstance().getDutyTypeDAO().findAll();
		DutyType dt=null;
		index=-1;
		for (int i=0;i<dutyTypes.size();i++){
			if ("любая".equals(dutyTypes.get(i).getName().toLowerCase())){
				index=i;
				break;
			}
		}
		if (index!=-1){
			dutyTypes.remove(index);
		}

		// load employers
		employers = new ArrayList<Employer>();
		employers = DAOUtil.getInstance().getEmployerDAO().findAll();

		DAOUtil.getInstance().commitTransaction();
	}

		
	public String getLoadPhase() {
		return loadPhase;
	}

	public void setLoadPhase(String loadPhase) {
		this.loadPhase = loadPhase;
	}

	public String getProcessAndSavePhase() {
		return processAndSavePhase;
	}

	public void setProcessAndSavePhase(String processAndSavePhase) {
		this.processAndSavePhase = processAndSavePhase;
	}

	public Long getRemainingTasks() {
		return remainingTasks;
	}

	public void setRemainingTasks(Long remainingTasks) {
		//this.remainingTasks = remainingTasks;
	}

	public String getSelectedExperience() {
		return selectedExperience;
	}

	public void setSelectedExperience(String selectedExperience) {
		this.selectedExperience = selectedExperience;
	}


	public String getSelectedSalaryFrom() {
		return selectedSalaryFrom;
	}

	public void setSelectedSalaryFrom(String selectedSalaryFrom) {
		this.selectedSalaryFrom = selectedSalaryFrom;
	}

	public String getSelectedSalaryTo() {
		return selectedSalaryTo;
	}

	public void setSelectedSalaryTo(String selectedSalaryTo) {
		this.selectedSalaryTo = selectedSalaryTo;
	}

	public String getSelectedDutyType() {
		return selectedDutyType;
	}

	public void setSelectedDutyType(String selectedDutyType) {
		this.selectedDutyType = selectedDutyType;
	}

	public String getSelectedEducation() {
		return selectedEducation;
	}

	public void setSelectedEducation(String selectedEducation) {
		this.selectedEducation = selectedEducation;
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

	public String getSelectedEmployer() {
		return selectedEmployer;
	}

	public void setSelectedEmployer(String selectedEmployer) {
		this.selectedEmployer = selectedEmployer;
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


	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}


	public List<DutyType> getDutyTypes() {
		return dutyTypes;
	}

	public void setDutyTypes(List<DutyType> dutyTypes) {
		this.dutyTypes = dutyTypes;
	}


	public List<Employer> getEmployers() {
		return employers;
	}

	public void setEmployers(List<Employer> employers) {
		this.employers = employers;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<String> getSelectedSections() {
		return selectedSections;
	}

	public void setSelectedSections(List<String> selectedSections) {
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
			pages.add(new PaginatorItem(1L,i));
		}

		currentSearchVacancies = DAOUtil.getInstance().getVacancyDAO().findManyPagination(page*PERPAGE, PERPAGE, restriction);

		//simpleSearchString.setValue("processed");

		DAOUtil.getInstance().commitTransaction();
		return ""; // stay on same page
	}

	public String doShowAdvancedSearchResults(Long page){
		List<Criterion> restrictions=new LinkedList<Criterion>();
		//restrictions=Restrictions.ilike("jobTitle", "%"+currentSimpleSearch+"%");

		String searchString=(String)simpleSearchString.getValue();

		log.info(String.format("searchString: %s",searchString));
		log.info(String.format("selectedDutyType: %s",selectedDutyType));
		log.info(String.format("selectedSalaryFrom: %s",selectedSalaryFrom));
		log.info(String.format("selectedSalaryTo",selectedSalaryTo));
		log.info(String.format("selectedEducation: %s",selectedEducation));
		log.info(String.format("selectedExperience: %s",selectedExperience));
		log.info(String.format("selectedSchedule: %s",selectedSchedule));
		log.info(String.format("selectedCity: %s",selectedCity));    
		log.info(String.format("selectedEmployer: %s",selectedEmployer));

		boolean newSearch=false;

		if (page==0){
			// this can be new search, so we need check all search fields
			currentSalaryFrom = currentSalaryFrom==null ? "" : currentSalaryFrom;
			currentSalaryTo = currentSalaryTo==null ? "" : currentSalaryTo;
			currentDutyType = currentDutyType==null ? "" : currentDutyType;
			currentEducation = currentEducation==null ? "" : currentEducation;
			currentExperience = currentExperience==null ? "" : currentExperience;
			currentSchedule = currentSchedule==null ? "" : currentSchedule;
			currentCity = currentCity==null ? "" : currentCity;
			currentEmployer = currentEmployer==null ? "" : currentEmployer;
			if (
					!currentSimpleSearch.equals(searchString) ||
					!currentSalaryFrom.equals(selectedSalaryFrom) ||
					!currentSalaryTo.equals(selectedSalaryTo) ||
					!currentDutyType.equals(selectedDutyType)||
					!currentEducation.equals(selectedEducation) ||
					!currentExperience.equals(selectedExperience) ||
					!currentSchedule.equals(selectedSchedule) ||
					!currentCity.equals(selectedCity) ||
					!currentEmployer.equals(selectedEmployer) || 
					!currentSections.equals(selectedSections)
					){
				// this is new search
				currentSimpleSearch=searchString;

				currentSalaryFrom=selectedSalaryFrom;
				currentSalaryTo=selectedSalaryTo;
				currentDutyType=selectedDutyType;
				currentEducation=selectedEducation;
				currentExperience=selectedExperience;
				currentSchedule=selectedSchedule;
				currentCity=selectedCity;
				currentEmployer=selectedEmployer;
				currentSections=selectedSections;

				newSearch=true;
			}
		}

		log.info(String.format("*** new Search %s ***",newSearch));

		boolean nonEmptyCriterion=false;

		if (currentSimpleSearch!=null && !"".equals(currentSimpleSearch)){
			restrictions.add(Restrictions.ilike("jobTitle", "%"+currentSimpleSearch+"%"));
			nonEmptyCriterion=true;
		}

		if (currentSalaryFrom!=null && currentSalaryTo!=null){
			if ( !"".equals(currentSalaryFrom) && !"".equals(currentSalaryTo)){
				restrictions.add(Restrictions.between("salary", Long.valueOf(currentSalaryFrom), Long.valueOf(currentSalaryTo)));
				nonEmptyCriterion=true;
			}
			if (!"".equals(currentSalaryFrom) && "".equals(currentSalaryTo)){
				restrictions.add(Restrictions.gt("salary", Long.valueOf(currentSalaryFrom)));
				nonEmptyCriterion=true;
			}
			if ("".equals(currentSalaryFrom) && !"".equals(currentSalaryTo)){
				restrictions.add(Restrictions.lt("salary", Long.valueOf(currentSalaryTo)));
				nonEmptyCriterion=true;
			}
		}

		if (currentDutyType!=null && !"".equals(currentDutyType)){
			Long dtId=Long.valueOf(currentDutyType);
			DutyType dt=null;
			for (int i=0;i<dutyTypes.size();i++){
				if (dutyTypes.get(i).getId().equals(dtId)){
					dt=dutyTypes.get(i);
					break;
				}
			}
			if (dt!=null){
				restrictions.add(Restrictions.eq("dutyType", dt));
				nonEmptyCriterion=true;
			}
			log.info(String.format("duty %s : %s of: %s ",dtId, dt,dutyTypes.size()));
		}

		if (currentEducation!=null && !"".equals(currentEducation)){
			Long edId=Long.valueOf(currentEducation);
			Education ed=null;
			for (int i=0;i<educations.size();i++){
				if (educations.get(i).getId().equals(edId)){
					ed=educations.get(i);
					break;
				}
			}
			if (ed!=null){
				restrictions.add(Restrictions.eq("education", ed));
				nonEmptyCriterion=true;
			}
		}
		if (currentExperience!=null && !"".equals(currentExperience)){
			Long exp=Long.valueOf(currentExperience);
			restrictions.add(Restrictions.le("experience", exp));
			nonEmptyCriterion=true;
		}
		if (currentSchedule!=null && !"".equals(currentSchedule)){
			Long scId=Long.valueOf(currentSchedule);
			Schedule sc=null; 
			for (int i=0;i<schedules.size();i++){
				if (schedules.get(i).getId().equals(scId)){
					sc=schedules.get(i);
					break;
				}
			}
			if (sc!=null){
				restrictions.add(Restrictions.eq("schedule", sc));
				nonEmptyCriterion=true;
			}
		}
		if (currentCity!=null && !"".equals(currentCity)){
			Long ciId=Long.valueOf(currentCity);
			City city=null;
			for (int i=0;i<cities.size();i++){
				if (cities.get(i).getId().equals(ciId)){
					city=cities.get(i);
					break;
				}
			}
			if (city!=null){
				restrictions.add(Restrictions.eq("city", city));
				nonEmptyCriterion=true;
			}
		}
		if (currentEmployer!=null && !"".equals(currentEmployer)){
			Long empId=Long.valueOf(currentEmployer);
			Employer emp=null;
			for (int i=0;i<employers.size();i++){
				if (employers.get(i).getId().equals(empId)){
					emp=employers.get(i);
					break;
				}
			}
			if (emp!=null){
				restrictions.add(Restrictions.eq("employer", emp));
				nonEmptyCriterion=true;
			}
		}

		if (currentSections!=null && currentSections.size()>0){
			log.info(currentSections.toString());
			Criterion sect=null;
			boolean first=true;
			for (String s:currentSections){
				for (int i=0;i<sections.size();i++){
					if (sections.get(i).getId().equals(Long.valueOf(s))){
						if (first){
							sect=Restrictions.eq("section", sections.get(i));
							first=false;
						}else{
							sect=Restrictions.or(sect, Restrictions.eq("section", sections.get(i)));
						}
					}
				}
			}
			if (sect!=null){
				restrictions.add(sect);
				nonEmptyCriterion=true;
			}
		}

		if (nonEmptyCriterion){

			DAOUtil.getInstance().beginTransaction();

			if (newSearch){ 
				log.info(restrictions.toString());
				Long totalVacancies = DAOUtil.getInstance().getVacancyDAO().countByCriterions(restrictions);

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
				pages.add(new PaginatorItem(2L, i));
			}

			currentSearchVacancies=DAOUtil.getInstance().getVacancyDAO().findManyPagination(page*PERPAGE, PERPAGE, restrictions);

			DAOUtil.getInstance().commitTransaction();
		}
		return "";
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

	public String doLoadVacancies(){
		LoadAndSave.getInstance().startLoad();
		return "";
	}
	
	public String doRefresh(){
		loadPhase = LoadAndSave.getInstance().getWholeSiteWorkerStatus().toString();
		processAndSavePhase = LoadAndSave.getInstance().getCollectAndStoreWorkerStatus().toString(); 
		return "";
	}
	
	public String doReloadCatalog(){
		init();
		return "";
	}

}
