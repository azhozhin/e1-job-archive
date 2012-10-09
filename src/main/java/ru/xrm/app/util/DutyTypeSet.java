package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.xrm.app.domain.DutyType;

public class DutyTypeSet {

	private static DutyTypeSet instance;
	private Long counter;
	Map<String, DutyType> dutyTypes;
	
	public static synchronized DutyTypeSet getInstance(){
		if (instance==null){
			instance = new DutyTypeSet();
		}
		return instance;
	}
	
	private DutyTypeSet(){
		counter=0L;
		dutyTypes=new HashMap<String, DutyType>();
	}

	public synchronized DutyType findOrCreate(String name){
		if (dutyTypes.containsKey(name)){
			return dutyTypes.get(name);
		}

		DutyType result =new DutyType(counter,name);
		dutyTypes.put(name,result);
		counter++;
		return result;
	}
	
	public synchronized List<DutyType> getDutyTypes(){
		List<DutyType> result=new ArrayList<DutyType>();
		for(DutyType dt:dutyTypes.values()){
			result.add(dt);
		}
		return result;
	}
	
}
