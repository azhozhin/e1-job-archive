package ru.xrm.app.transformers;

public class ToLong implements PropertyTransformer {

	public Object transform(String from) {
		return Long.valueOf(from);
	}

}
