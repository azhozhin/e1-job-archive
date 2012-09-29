package ru.xrm.app.evaluators;

public class JobBodyPropertyElementEvaluator implements ElementEvaluator {

	public String evaluate(org.jsoup.nodes.Element element) {
		return element.html();
	}

}
