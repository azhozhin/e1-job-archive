package ru.xrm.app.walkers;

import org.jsoup.nodes.Element;


public class VacancyPropertyElementWalker implements ElementWalker {

	public Element walk(Element element) {
		return element.parent().parent().child(1);
	}

}
