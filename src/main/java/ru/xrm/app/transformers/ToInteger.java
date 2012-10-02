package ru.xrm.app.transformers;

public class ToInteger implements PropertyTransformer {

	public Object transform(String from) {
		return Integer.valueOf(from);
	}

}
