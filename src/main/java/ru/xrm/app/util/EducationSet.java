package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.xrm.app.domain.Education;

public class EducationSet {

	private static EducationSet instance;
	private Map<String,Education> educations;
	private Long counter;
	
	public static synchronized EducationSet getInstance(){
		if (instance==null){
			instance=new EducationSet();
		}
		return instance;
	}
	
	private EducationSet(){
		educations = new HashMap<String, Education>();
		counter=0L;
	}

	public synchronized Education findOrCreate(String name) {
		if (educations.containsKey(name)){
			return educations.get(name);
		}
		
		Education result=new Education(counter,name);
		educations.put(name, result);
		counter++;
		return result;
	}
	
	public synchronized List<Education> getEducations(){
		List<Education> result=new ArrayList<Education>();
		for (Education e:educations.values()){
			result.add(e);
		}
		return result;
	}
	
}
