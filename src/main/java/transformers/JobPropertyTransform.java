package transformers;

import org.jsoup.nodes.Element;

import ru.xrm.app.ElementTransform;

public class JobPropertyTransform implements ElementTransform {

	public Element transform(Element element) {
		return element.parent().parent().child(1);
	}

}
