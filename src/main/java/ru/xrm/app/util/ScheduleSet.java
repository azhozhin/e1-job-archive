package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.xrm.app.domain.Schedule;

public class ScheduleSet {

	private static ScheduleSet instance;
	private Map<String,Schedule> schedules;
	private Long counter;
	
	public static ScheduleSet getInstance(){
		if (instance==null){
			instance=new ScheduleSet();
		}
		return instance;
	}
	
	public ScheduleSet(){
		schedules=new HashMap<String,Schedule>();
		counter=0L;
	}
	
	public synchronized Schedule findOrCreate(String name){
		if (schedules.containsKey(name)){
			return schedules.get(name);
		}
		Schedule result=new Schedule(counter,name);
		schedules.put(name,result);
		counter++;
		return result;
	}
	
	public synchronized List<Schedule> getSchedules(){
		List<Schedule> result=new ArrayList<Schedule>();
		for (Schedule s:schedules.values()){
			result.add(s);
		}
		return result;
	}
}
