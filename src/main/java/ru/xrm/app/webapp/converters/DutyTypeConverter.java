package ru.xrm.app.webapp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ru.xrm.app.domain.DutyType;
import ru.xrm.app.util.DAOUtil;

@FacesConverter(value="dutyTypeConverter")
public class DutyTypeConverter implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		DAOUtil.getInstance().beginTransaction();
		DutyType result=DAOUtil.getInstance().getDutyTypeDAO().findByID(DutyType.class, Long.valueOf(value));
		DAOUtil.getInstance().commitTransaction();
		return result;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value==null){
			return "";
		}else{
			return value.toString();
		}
	}

}
