package ru.xrm.app.util;

import java.lang.reflect.Field;

public class VacancyLink {

	private String href;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
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
