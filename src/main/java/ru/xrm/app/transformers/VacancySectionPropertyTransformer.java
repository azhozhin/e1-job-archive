package ru.xrm.app.transformers;

import ru.xrm.app.domain.Section;
import ru.xrm.app.util.SectionSet;

public class VacancySectionPropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		Section result;
		SectionSet sectionList=SectionSet.getInstance();
		
		if (sectionList.exists(from)){
			result=sectionList.getByName(from);
		}else{
			return null;
		}
		
		return result;
	}

}
