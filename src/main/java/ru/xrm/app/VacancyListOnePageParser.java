package ru.xrm.app;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.xrm.app.config.Config;
import ru.xrm.app.config.Entry;
import ru.xrm.app.domain.VacancyLink;
import ru.xrm.app.domain.VacancyPage;

public class VacancyListOnePageParser {

	Config config;
	String html;

	public VacancyListOnePageParser(Config config, String html){
		this.config = config;
		this.html = html;
	}

	public List<VacancyLink> parse(){
		List<VacancyLink> links = new ArrayList<VacancyLink>();

		Document doc=Jsoup.parse(html);

		List<Entry> vacancyListProperties = config.getVacancyListProperties();

		for (Entry prop:vacancyListProperties){
			// here we got multiple elements for each vacancySectionProperty
			Elements elems=doc.select(prop.getCssQuery());
			Object value="";
			int idx=0;
			for (Element e:elems){

				if (prop.getElementWalker() != null){
					e=prop.getElementWalker().walk(e);
				}
				if (prop.getElementEvaluator() !=null){
					value=prop.getElementEvaluator().evaluate(e);
				}
				if (prop.getPropertyTransformer() !=null){
					value = prop.getPropertyTransformer().transform(value.toString());
				}

				VacancyLink vacancyLink;

				// if object of some index does not exists, create it and add to result
				try{
					vacancyLink=links.get(idx);
				}catch(IndexOutOfBoundsException e1){
					vacancyLink=new VacancyLink();
					links.add(idx, vacancyLink);
				}			

				// fill property for current object
				try {
					vacancyLink.setProperty(prop.getKey(), value);
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}

				idx++;
			}
		}
		return links;
	}
	
	public List<VacancyPage> getPages(){
		List<VacancyPage> pages=new ArrayList<VacancyPage>();
		List<Entry> paginatorProperties=config.getVacancyListPaginatorProperties();
		Document doc=Jsoup.parse(html);
		
		for (Entry prop:paginatorProperties){
			Elements elem=doc.select(prop.getCssQuery());
			
		}
		return pages;
	}
}
