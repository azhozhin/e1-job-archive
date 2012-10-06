package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.List;

import ru.xrm.app.domain.City;

public class CitySet {

	private static CitySet instance;
	private List<City> cities;
	private Long counter;
	
	public static synchronized CitySet getInstance(){
		if (instance==null){
			instance=new CitySet();
		}
		return instance;
	}
	
	private CitySet(){
		cities=new ArrayList<City>();
		counter=0L;
	}
	
	public synchronized City findOrCreate(String name){
		for (City c:cities){
			if (c.getName().equals(name)){
				return c;
			}
		}
		City result=new City(counter,name);
		cities.add(result);
		counter++;
		return result;
	}
	
	public synchronized List<City> getCities() {
		return cities;		
	}
}
