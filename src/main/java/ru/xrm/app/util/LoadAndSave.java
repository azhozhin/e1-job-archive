package ru.xrm.app.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.threads.CollectAndStoreWorker;
import ru.xrm.app.threads.WholeSiteWorker;

public class LoadAndSave {
	
	protected Logger log = LoggerFactory.getLogger( LoadAndSave.class );

	private static LoadAndSave instance;
	private Long workerCount;
	private Boolean isRunning;
	private Thread wholeSiteWorkerThread;
	private Thread collectAndStoreThread;
	private LinkedList<Future<List<Vacancy>>> allVacancyListParts;
	
	public static synchronized LoadAndSave getInstance(){
		if (instance==null){
			instance=new LoadAndSave();
		}
		return instance;
	}
	
	private LoadAndSave(){
		workerCount=0L;
		isRunning=false;
	}
	
	public synchronized Long getWorkerCount(){
		return workerCount;
	}
	
	public synchronized void startLoad(){
		if (isRunning){
			log.info("Loading already started");
			return;
		}
		
		isRunning=true;
		
		allVacancyListParts=new LinkedList<Future<List<Vacancy>>>();
		WholeSiteWorker wholeSiteWorker = new WholeSiteWorker(allVacancyListParts, "windows-1251", 2);

		wholeSiteWorkerThread=new Thread(wholeSiteWorker);

		wholeSiteWorkerThread.start();
	
		CollectAndStoreWorker collectAndStoreWorker = new CollectAndStoreWorker(wholeSiteWorkerThread, allVacancyListParts, isRunning);
		
		collectAndStoreThread=new Thread(collectAndStoreWorker);
		
		collectAndStoreThread.start();
	}
	
	public synchronized Thread.State getWholeSiteWorkerStatus(){
		if (wholeSiteWorkerThread==null){
			return Thread.State.TERMINATED;
		}else{
			return wholeSiteWorkerThread.getState();
		}
	}
	public synchronized Thread.State getCollectAndStoreWorkerStatus(){
		if (collectAndStoreThread==null){
			return Thread.State.TERMINATED;
		}else{
			return collectAndStoreThread.getState();
		}
	}
	
}
