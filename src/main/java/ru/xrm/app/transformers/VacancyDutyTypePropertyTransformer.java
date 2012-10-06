package ru.xrm.app.transformers;

import ru.xrm.app.misc.DutyTypeSet;

public class VacancyDutyTypePropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		return DutyTypeSet.getInstance().findOrCreate(from);
	}

}
