package ru.xrm.app.transformers;

import ru.xrm.app.util.ScheduleSet;

public class VacancySchedulePropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		return ScheduleSet.getInstance().findOrCreate(from);
	}

}
