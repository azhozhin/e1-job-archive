package ru.xrm.app.transformers;

import ru.xrm.app.util.CitySet;

public class VacancyCityPropertyTransformer implements PropertyTransformer {

	public Object transform(String from) {
		return CitySet.getInstance().findOrCreate(from);
	}

}
