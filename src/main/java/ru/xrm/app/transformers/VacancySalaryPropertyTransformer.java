package ru.xrm.app.transformers;

public class VacancySalaryPropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		Long result=-1L;
		String[] parts=from.split(" ");
		if (parts.length==2){
			result=Long.valueOf(parts[0]);
		}
		return result;
	}

}
