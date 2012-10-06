package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.List;

import ru.xrm.app.domain.Schedule;

public class ScheduleSet {

	private static ScheduleSet instance;
	private List<Schedule> schedules;
	private Long counter;
	
	public static ScheduleSet getInstance(){
		if (instance==null){
			instance=new ScheduleSet();
		}
		return instance;
	}
	
	public ScheduleSet(){
		schedules=new ArrayList<Schedule>();
		counter=0L;
	}
	
	public synchronized Schedule findOrCreate(String name){
		for (Schedule s:schedules){
			if (s.getName().equals(name)){
				return s;
			}
		}
		Schedule result=new Schedule(counter,name);
		schedules.add(result);
		counter++;
		return result;
	}
	
	public synchronized List<Schedule> getSchedules(){
		return schedules;
	}
}
