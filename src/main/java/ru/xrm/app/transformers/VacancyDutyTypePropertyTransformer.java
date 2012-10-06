package ru.xrm.app.transformers;

import ru.xrm.app.util.DutyTypeSet;

public class VacancyDutyTypePropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		return DutyTypeSet.getInstance().findOrCreate(from);
	}

}
