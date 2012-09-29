package ru.xrm.app.walkers;

import org.jsoup.nodes.Element;

public class VacancySalaryElementWalker implements ElementWalker {

	public Element walk(Element element) {
		return element.parent().parent().parent().child(1);
	}

}
