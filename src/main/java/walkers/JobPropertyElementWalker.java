package walkers;

import org.jsoup.nodes.Element;


public class JobPropertyElementWalker implements ElementWalker {

	public Element walk(Element element) {
		return element.parent().parent().child(1);
	}

}
