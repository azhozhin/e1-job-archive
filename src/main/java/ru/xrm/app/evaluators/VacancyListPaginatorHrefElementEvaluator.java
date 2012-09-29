package ru.xrm.app.evaluators;

import org.jsoup.nodes.Element;

public class VacancyListPaginatorHrefElementEvaluator implements
		ElementEvaluator {

	public String evaluate(Element element) {
		return element.attr("href");
	}

}
