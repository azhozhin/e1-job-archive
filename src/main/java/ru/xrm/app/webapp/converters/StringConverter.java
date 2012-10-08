package ru.xrm.app.webapp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value="stringConverter")
public class StringConverter implements Converter{
 
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value;
	}
 
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String result;
		if (value==null){
			result="";
		}else{
			result=value.toString();
		}
 		return result;
 	}	
}