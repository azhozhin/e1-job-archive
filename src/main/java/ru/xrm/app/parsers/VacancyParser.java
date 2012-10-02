package ru.xrm.app.parsers;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.xrm.app.config.Config;
import ru.xrm.app.config.Entry;
import ru.xrm.app.domain.Vacancy;

public class VacancyParser {

	Config config;
	String html;

	public VacancyParser(Config config, String html){
		this.config = config;
		this.html = html;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Vacancy parse(){
		Vacancy vacancy=new Vacancy();

		List<Entry> vacancyProperties=config.getVacancyProperties();

		Document doc = Jsoup.parse(html);

		for (Entry prop:vacancyProperties){
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
					vacancy.setProperty(prop.getKey(), value);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return vacancy;		
	}
}
