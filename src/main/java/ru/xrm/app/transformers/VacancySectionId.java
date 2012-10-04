package ru.xrm.app.transformers;

public class VacancySectionId implements PropertyTransformer {

	public Object transform(String from) {
		String[] parts=from.split("=");
		Long result=Long.valueOf(parts[parts.length-1]);
		return result;
	}

}
