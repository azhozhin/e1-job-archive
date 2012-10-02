package ru.xrm.app.evaluators;

import org.jsoup.nodes.Element;

public class VacancyIdElementEvaluator implements ElementEvaluator {

	public String evaluate(Element element) {
		String[] parts=element.attr("value").split("=");
		return parts[parts.length-1];
	}

}
