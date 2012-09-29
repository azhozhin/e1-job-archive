package ru.xrm.app.evaluators;

import org.jsoup.nodes.Element;

public class VacancySectionNameElementEvaluator implements ElementEvaluator {

	public String evaluate(Element element) {
		return element.text();
	}

}
