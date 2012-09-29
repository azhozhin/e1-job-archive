package ru.xrm.app.transformers;

public class VacancySalaryPropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		Integer result=-1;
		String[] parts=from.split(" ");
		if (parts.length==2){
			result=Integer.valueOf(parts[0]);
		}
		return result;
	}

}
