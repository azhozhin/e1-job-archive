package ru.xrm.app.transformers;

import ru.xrm.app.misc.EducationSet;

public class VacancyEducationPropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		return EducationSet.getInstance().findOrCreate(from);
	}

}
