package ru.xrm.app.evaluators;

public class GetHtml implements ElementEvaluator {

	public String evaluate(org.jsoup.nodes.Element element) {
		return element.html();
	}

}
