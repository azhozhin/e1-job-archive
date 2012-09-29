package ru.xrm.app.walkers;

import org.jsoup.nodes.Element;

public class VacancyContactInformationPropertyElementWalker implements
		ElementWalker {

	public Element walk(Element element) {
		return element.parent();
	}

}
