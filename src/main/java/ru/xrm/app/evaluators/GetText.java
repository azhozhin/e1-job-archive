package ru.xrm.app.evaluators;


public class GetText implements ElementEvaluator {

	public String evaluate(org.jsoup.nodes.Element element) {
		return element.text();
	}

}
