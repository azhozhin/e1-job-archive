package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.List;

import ru.xrm.app.domain.DutyType;

public class DutyTypeSet {

	private static DutyTypeSet instance;
	private Long counter;
	List<DutyType> dutyTypes;
	
	public static synchronized DutyTypeSet getInstance(){
		if (instance==null){
			instance = new DutyTypeSet();
		}
		return instance;
	}
	
	private DutyTypeSet(){
		counter=0L;
		dutyTypes=new ArrayList<DutyType>();
	}

	public synchronized DutyType findOrCreate(String name){
		for (DutyType dt:dutyTypes){
			if (dt.getName().equals(name)){
				return dt;
			}
		}
		DutyType result =new DutyType(counter,name);
		dutyTypes.add(result);
		counter++;
		return result;
	}
	
	public synchronized List<DutyType> getDutyTypes(){
		return dutyTypes;
	}
	
}
