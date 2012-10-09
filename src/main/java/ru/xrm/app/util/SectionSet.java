package ru.xrm.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.xrm.app.domain.Section;

public class SectionSet {
	private static SectionSet instance;
	private List<Section> sections;

	public static synchronized SectionSet getInstance(){
		if(instance==null){
			instance=new SectionSet();
		}
		return instance;
	}
	
	private SectionSet(){
		reset();
	}
	
	public synchronized void reset(){
		sections=new ArrayList<Section>();
	}
	
	public synchronized boolean exists(String name){
		for (Section s:sections){
			if (s.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public synchronized void add(Section section){
		sections.add(section);
	}
	
	public synchronized Section getByName(String name){
		for (Section s:sections){
			if (s.getName().equals(name)){
				return s;
			}
		}
		return null;
	}
	
	public synchronized Section getByIndex(Long idx){
		if (idx<0 || idx>=sections.size()){
			return null;
		}
		return sections.get(idx.intValue());
	}
	
	public synchronized List<Section> getSections(){
		return sections;
	}
	
	

}
