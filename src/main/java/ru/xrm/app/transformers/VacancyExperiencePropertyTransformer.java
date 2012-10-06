package ru.xrm.app.transformers;

public class VacancyExperiencePropertyTransformer implements
		PropertyTransformer {

	public Object transform(String from) {
		Integer result;
		String[] parts=from.split(" ");
		if (parts.length==2){
			result=Integer.valueOf(parts[0]);
			if (result<0){
				result=0;
			}
		}else{
			result=0;
		}
		return result;
	}

}
