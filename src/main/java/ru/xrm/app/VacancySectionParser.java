package ru.xrm.app;

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
	
	public VacancySectionParser(Config config, String html){
		this.config = config;
		this.html = html;
	}
	
	public VacancySection parse(){
		VacancySection section = new VacancySection();
		
		List<Entry> vacancySectionProperties=config.getVacancySectionProperties();
		
		Document doc = Jsoup.parse(html);
		
		for (Entry prop:vacancySectionProperties){
			Elements elems=doc.select(prop.getCssQuery());
			Object value="";
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

				try{
					section.setProperty(prop.getKey(), value);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return section;
	}
}
