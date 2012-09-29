package ru.xrm.app.config;

import java.util.List;

import ru.xrm.app.transformers.PropertyTransformer;
import ru.xrm.app.walkers.ElementWalker;

public class VacancyProperty {
	String key;
	String cssQuery;
	List<CSSQueryArg> cssQueryArgs;
	ElementWalker elementWalker;
	PropertyTransformer propertyTransformer;
	
	public VacancyProperty(String key, String cssQuery, List<CSSQueryArg> cssQueryArgs, 
			ElementWalker elementWalker, PropertyTransformer propertyTransformer){
		this.key = key;
		this.cssQuery = cssQuery;
		this.cssQueryArgs = cssQueryArgs;
		this.elementWalker = elementWalker;
		this.propertyTransformer = propertyTransformer;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCssQuery() {
		return cssQuery;
	}

	public void setCssQuery(String cssQuery) {
		this.cssQuery = cssQuery;
	}

	public List<CSSQueryArg> getCssQueryArgs() {
		return cssQueryArgs;
	}

	public void setCssQueryArgs(List<CSSQueryArg> cssQueryArgs) {
		this.cssQueryArgs = cssQueryArgs;
	}

	public ElementWalker getElementWalker() {
		return elementWalker;
	}

	public void setElementWalker(ElementWalker elementWalker) {
		this.elementWalker = elementWalker;
	}

	public PropertyTransformer getPropertyTransformer() {
		return propertyTransformer;
	}

	public void setPropertyTransformer(PropertyTransformer propertyTransformer) {
		this.propertyTransformer = propertyTransformer;
	}
	
}
