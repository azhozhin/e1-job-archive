package ru.xrm.app.transformers;

public class VacancyExperiencePropertyTransformer implements
		PropertyTransformer {

	public Object transform(String from) {
		Long result;
		String[] parts=from.split(" ");
		if (parts.length==2){
			result=Long.valueOf(parts[0]);
			if (result<0){
				result=0L;
			}
		}else{
			result=0L;
		}
		return result;
	}

}
