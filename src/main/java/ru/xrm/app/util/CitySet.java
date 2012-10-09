package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.xrm.app.domain.City;

public class CitySet {

	private static CitySet instance;
	private Map<String, City> cities;
	private Long counter;
	
	public static synchronized CitySet getInstance(){
		if (instance==null){
			instance=new CitySet();
		}
		return instance;
	}
	
	private CitySet(){
		cities=new HashMap<String, City>();
		counter=0L;
	}
	
	public synchronized City findOrCreate(String name){
		if (cities.containsKey(name)){
			return cities.get(name);
		}
		
		City result=new City(counter,name);
		cities.put(name,result);
		counter++;
		return result;
	}
	
	public synchronized List<City> getCities() {
		List<City> result=new ArrayList<City>();
		for (City c:cities.values()){
			result.add(c);
		}
		return result;		
	}
}
