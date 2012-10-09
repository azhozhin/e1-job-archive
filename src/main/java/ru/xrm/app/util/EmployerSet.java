package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.xrm.app.domain.Employer;

public class EmployerSet {

	private static EmployerSet instance;
	private Map<String,Employer> employers;
	private Long counter;
	
	public static synchronized EmployerSet getInstance(){
		if (instance==null){
			instance=new EmployerSet();
		}
		return instance;
	}
	
	private EmployerSet(){
		employers = new HashMap<String,Employer>();
		counter=0L;
	}

	public synchronized Employer findOrCreate(String name) {
		if (employers.containsKey(name)){
			return employers.get(name);
		}
		Employer result=new Employer(counter,name);
		employers.put(name,result);
		counter++;
		return result;
	}
	
	public synchronized List<Employer> getEmployers(){
		List<Employer> result=new ArrayList<Employer>();
		for (Employer e:employers.values()){
			result.add(e);
		}
		return result;
	}
	
}
