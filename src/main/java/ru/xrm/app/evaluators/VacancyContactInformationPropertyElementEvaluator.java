package ru.xrm.app.evaluators;

import org.jsoup.nodes.Element;

public class VacancyContactInformationPropertyElementEvaluator implements
		ElementEvaluator {

	public String evaluate(Element element) {
		String result=element.text();
		result=result.substring("Контактная информация: ".length(), result.length());
		return result;
	}

}
