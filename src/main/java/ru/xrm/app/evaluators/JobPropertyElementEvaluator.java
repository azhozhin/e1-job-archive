package ru.xrm.app.evaluators;


public class JobPropertyElementEvaluator implements ElementEvaluator {

	public String evaluate(org.jsoup.nodes.Element element) {
		return element.text();
	}

}
