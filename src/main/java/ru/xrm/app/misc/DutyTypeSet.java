package ru.xrm.app.misc;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dutyTypes == null) ? 0 : dutyTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DutyTypeSet other = (DutyTypeSet) obj;
		if (dutyTypes == null) {
			if (other.dutyTypes != null)
				return false;
		} else if (!dutyTypes.equals(other.dutyTypes))
			return false;
		return true;
	}
	
}
