package ru.xrm.app.domain;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DutyType {
	
	@Id
	@Column(name="dutytype_id")
	private Long id;
	
	@Column(name="name")
	private String name;

	public DutyType(){
		this(0L,"");
	}
	
	public DutyType(Long id, String name){
		this.id=id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setProperty(String property, Object value)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		@SuppressWarnings("rawtypes")
		Class aClass = getClass();
		Field field = aClass.getDeclaredField(property);
		field.set(this, value);
	}
}
