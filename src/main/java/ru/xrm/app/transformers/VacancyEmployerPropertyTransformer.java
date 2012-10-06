package ru.xrm.app.transformers;

import ru.xrm.app.util.EmployerSet;

public class VacancyEmployerPropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		return EmployerSet.getInstance().findOrCreate(from);
	}

}
