package ru.xrm.app;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.xrm.app.config.Config;
import ru.xrm.app.config.Entry;
import ru.xrm.app.domain.VacancySection;

public class VacancySectionParser {

	Config config;
	String html;
	VacancySection sections;
	
	public VacancySectionParser(Config config, String html){
		this.config = config;
		this.html = html;
	}
	
	public List<VacancySection> parse() {
		List<VacancySection> sections = new ArrayList<VacancySection>();
	
		List<Entry> vacancySectionProperties=config.getVacancySectionProperties();
			
		Document doc = Jsoup.parse(html);
		for (Entry prop:vacancySectionProperties){
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
				
				VacancySection section;
				
				try{
					section=sections.get(idx);
				}catch(IndexOutOfBoundsException e1){
					section=new VacancySection();
					sections.add(idx, section);
				}			
				
				try {
					section.setProperty(prop.getKey(), value);
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
		return sections;
	}
}
