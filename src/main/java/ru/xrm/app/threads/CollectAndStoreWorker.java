package ru.xrm.app.threads;

import java.lang.Thread.State;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.xrm.app.domain.City;
import ru.xrm.app.domain.DutyType;
import ru.xrm.app.domain.Education;
import ru.xrm.app.domain.Employer;
import ru.xrm.app.domain.Schedule;
import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.util.CitySet;
import ru.xrm.app.util.DutyTypeSet;
import ru.xrm.app.util.EducationSet;
import ru.xrm.app.util.EmployerSet;
import ru.xrm.app.util.HibernateUtil;
import ru.xrm.app.util.ScheduleSet;
import ru.xrm.app.util.SectionSet;

public class CollectAndStoreWorker implements Runnable{

	protected Logger log = LoggerFactory.getLogger( CollectAndStoreWorker.class );
	
	private Thread predecessorThread;
	private LinkedList<Future<List<Vacancy>>> allVacancyListParts;
	private Boolean isRunning;

	public CollectAndStoreWorker(Thread predecessorThread, LinkedList<Future<List<Vacancy>>> allVacancyListParts, Boolean isRunning){
		this.predecessorThread=predecessorThread;
		this.allVacancyListParts = allVacancyListParts;
		this.isRunning=isRunning;
	}

	public void run() {
		// Sleep before waiting other thread
		try{
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(predecessorThread.getState()==State.RUNNABLE){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		log.info("Wait for child thread to finish");
		
		// Wait for all threads ends
		boolean allReady=false;

		int threadCounter;
		
		do{
			threadCounter=0;
			allReady=true;

			for (Future<List<Vacancy>> f:allVacancyListParts){
				if (!f.isDone()){
					allReady=false;
					threadCounter++;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			log.info(String.format("\n remaining tasks: %d \n", threadCounter));

		}while(!allReady);

		List<Vacancy> allVacancies=new LinkedList<Vacancy>();
		for (Future<List<Vacancy>> f:allVacancyListParts){
			try {
				List<Vacancy> lv=f.get();
				for (int i=0;i<lv.size();i++){
					log.info(String.format("%d ",lv.get(i).getId()));
				}
				allVacancies.addAll(lv);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		log.info(String.format("Total vacancies: %s\n", allVacancies.size()));

		log.info(String.format("Vacancy and count\n"));
		Map<String,Integer> stats=new HashMap<String,Integer>();
		for (Vacancy v:allVacancies){
			//System.out.print(v);
			if (v.getSection()==null){
				log.error(String.format("There is empty section for vacancy\n"));
				continue;
			}else{
				String key=v.getSection().getName();
				Integer value;
				if (!stats.containsKey(key)){
					value=1;
				}else{
					value=stats.get(key)+1;
				}
				stats.put(key, value);
			}
		}

		for(String vacancy:stats.keySet()){
			log.info(String.format("%s \t %s\n",vacancy,stats.get(vacancy)));
		}


		Date d1=new Date();
		log.info("Start storing to database");
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		// get stored section, delete them
		session.beginTransaction();

		session.createQuery("delete from Vacancy").executeUpdate();

		session.createQuery("delete from Section").executeUpdate();

		session.createQuery("delete from DutyType").executeUpdate();
		session.createQuery("delete from Education").executeUpdate();
		session.createQuery("delete from Schedule").executeUpdate();
		session.createQuery("delete from City").executeUpdate();
		session.createQuery("delete from Employer").executeUpdate();

		session.getTransaction().commit();

		// save
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		for (DutyType dt:DutyTypeSet.getInstance().getDutyTypes()){
			session.save(dt);
		}

		for (Education e:EducationSet.getInstance().getEducations()){
			session.save(e);
		}

		for (Schedule sc:ScheduleSet.getInstance().getSchedules()){
			session.save(sc);
		}

		for (City ci:CitySet.getInstance().getCities()){
			session.save(ci);
		}

		for (Employer em:EmployerSet.getInstance().getEmployers()){
			session.save(em);
		}

		for (Section s:SectionSet.getInstance().getSections()){
			session.save(s);
		}
		int c=0;
		int dups=0;
		int upds=0;
		Set<Vacancy> vacs=new HashSet<Vacancy>();
		for (Vacancy v:allVacancies){
			c++;

			if (vacs.contains(v)){
				log.info(String.format("Duplicate id %s\n",v.getId()));
				dups++;
				continue;
			}else{
				upds++;
				if (v.getId()==null){
					log.info("There is vacancy without ID");
				}else{
					session.save(v);
					vacs.add(v);
				}
			}

			if (c%20==0){
				session.flush();
				session.clear();
			}
		}
		session.getTransaction().commit();
		log.info(String.format("Done!"));
		Date d2=new Date();
		log.info(String.format("\n %d ms. \n",d2.getTime()-d1.getTime()));
		log.info(String.format("Duplicates %d Updates %d\n", dups, upds));

		allVacancies=null;
		allVacancyListParts=null;

		isRunning=false;
	}

}
