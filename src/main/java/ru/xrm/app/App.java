package ru.xrm.app;

import java.lang.Thread.State;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
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

import org.hibernate.Session;

import ru.xrm.app.config.Config;
import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.misc.HibernateUtil;
import ru.xrm.app.misc.SectionSet;
import ru.xrm.app.threads.WholeSiteWorker;

public class App 
{
	static final int THREAD_NUMBER = 2;
	static final String ENCODING = "windows-1251";

	public static void main( String[] args )
	{
		System.out.println(java.lang.Runtime.getRuntime().maxMemory()); 

		Config config=Config.getInstance();
		try{
			config.load("config.xml");
		}catch(Exception e){
			e.printStackTrace();
		}

		List<Future<List<Vacancy>>> allVacancyListParts=new LinkedList<Future<List<Vacancy>>>();

		WholeSiteWorker wholeSiteWorker = new WholeSiteWorker(allVacancyListParts, ENCODING, THREAD_NUMBER);

		Thread wholeSiteWorkerThread=new Thread(wholeSiteWorker);

		wholeSiteWorkerThread.start();

		// Wait for all threads ends
		boolean allReady=false;

		int threadCounter;
		
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
	    


		do{
			threadCounter=0;
			allReady=true;
			
			long[] threadIds = threadBean.findMonitorDeadlockedThreads();
		    int deadlockedThreads = threadIds != null? threadIds.length : 0;
		    
			try{
				for (Future<List<Vacancy>> f:allVacancyListParts){
					if (!f.isDone()){
						allReady=false;
						threadCounter++;
					}
				}
			}catch(ConcurrentModificationException e){
				continue;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		    
			System.out.format("\n remaining tasks: %d deadlocked threads: %d\n", threadCounter,deadlockedThreads);
			if (wholeSiteWorkerThread.getState()==State.RUNNABLE){
				allReady=false;
			}

		}while(!allReady);

		List<Vacancy> allVacancies=new LinkedList<Vacancy>();
		for (Future<List<Vacancy>> f:allVacancyListParts){
			try {
				List<Vacancy> lv=f.get();
				System.out.print("[");
				for (int i=0;i<lv.size();i++){
					System.out.format("%d ",lv.get(i).getId());
				}
				System.out.println("]");
				allVacancies.addAll(lv);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.format("Total vacancies: %s\n", allVacancies.size());

		System.out.format("Vacancy and count\n");
		Map<String,Integer> stats=new HashMap<String,Integer>();
		for (Vacancy v:allVacancies){
			//System.out.print(v);
			if (v.getSection()==null){
				System.err.format("There is empty section for vacancy\n");
				//System.err.println(v);
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
			System.out.format("%s \t %s\n",vacancy,stats.get(vacancy));
		}

		Date d1=new Date();
		System.out.println("Start storing to database");
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		// get stored section, delete them
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<Section> allStoredSection = session.createQuery("from Section").list();

		@SuppressWarnings("unchecked")		
		List<Vacancy> allStoredVacancies = session.createQuery("from Vacancy").list();

		allStoredSection.clear();
		allStoredVacancies.clear();
		session.getTransaction().commit();

		session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Section> sections=SectionSet.getInstance().getSections();
		for (Section s:sections){
			session.saveOrUpdate(s);
		}
		int c=0;
		int dups=0;
		int upds=0;
		Set<Vacancy> vacs=new HashSet<Vacancy>();
		for (Vacancy v:allVacancies){
			c++;

			if (vacs.contains(v)){
				System.out.format("Duplicate id %s\n",v.getId());
				dups++;
				continue;
			}
			else{
				upds++;
				session.saveOrUpdate(v);
				vacs.add(v);
			}

			if (c%20==0){
				session.flush();
				session.clear();
			}
		}
		session.getTransaction().commit();
		System.out.println("Done!");
		Date d2=new Date();
		System.out.format("\n %d ms. \n",d2.getTime()-d1.getTime());
		System.out.format("Duplicates %d Updates %d\n", dups, upds);
	}
}
