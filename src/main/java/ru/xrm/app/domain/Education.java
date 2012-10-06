package ru.xrm.app.domain;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Education {
	
	@Id
	@Column(name="education_id")
	private Long id;
	
	@Column(name="name")
	private String name;

	public Education(){
		this(0L, "");
	}
	
	public Education(Long id, String name){
		this.id=id;
		this.name=name;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Education other = (Education) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
