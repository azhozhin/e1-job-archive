package ru.xrm.app.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import ru.xrm.app.App;
import ru.xrm.app.evaluators.ElementEvaluator;
import ru.xrm.app.transformers.PropertyTransformer;
import ru.xrm.app.walkers.ElementWalker;


public class Config {

	Map<String,NamedCSSQuery> namedQueries;
	List<Entry> vacancySectionProperties;
	List<Entry> vacancyListProperties;
	List<Entry> vacancyProperties;
	
	private void init(){
		namedQueries=new HashMap<String,NamedCSSQuery>();
		vacancySectionProperties=new ArrayList<Entry>();
		vacancyListProperties=new ArrayList<Entry>();
		vacancyProperties=new ArrayList<Entry>();
	}
	
	public void load(String filename) throws Exception{
		init();
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;
		
		builder = factory.newDocumentBuilder();
		document=builder.parse(new File(filename));
		
		NodeList nodes=document.getChildNodes();
		
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			//System.out.format("%s \n",n.getNodeName());
			loadProperties(n);
		}
	}
	
	private void loadProperties(Node node) throws Exception{
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				if (n.getNodeName().equals("cssNamedQueries")){
					loadCSSNamedQueries(n);
				}else if (n.getNodeName().equals("vacancySectionProperties")){
					loadVacancySectionProperties(n);
				}else if (n.getNodeName().equals("vacancyListProperties")){
					loadVacancyListProperties(n);
				}else if (n.getNodeName().equals("vacancyListPaginatorProperties")){
					loadVacancyPaginatorProperties(n);
				}else if (n.getNodeName().equals("vacancyProperties")){
					loadVacancyProperties(n);
				}
			}
		}
	}

	private void loadCSSNamedQueries(Node node) {
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				NamedNodeMap attributes=n.getAttributes();
				String queryName=attributes.getNamedItem("name").getNodeValue();
				NodeList namedQueryNodes=n.getChildNodes();
				String cssQuery="";
				int cssArgsCount=0;
				for (int j=0;j<namedQueryNodes.getLength();j++){
					Node nq=namedQueryNodes.item(j);
					if (nq.getNodeType()==Node.ELEMENT_NODE){
						if ("cssQuery".equals(nq.getNodeName())){
							cssQuery = nq.getTextContent();
						}else if ("cssArgsCount".equals(nq.getNodeName())){
							cssArgsCount = Integer.valueOf(nq.getTextContent());
						}
					}
				}
				namedQueries.put(queryName,new NamedCSSQuery(queryName,cssQuery, cssArgsCount));
			}
		}
	}
	
	private void loadVacancySectionProperties(Node node) throws Exception {
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){				
				vacancySectionProperties.add(loadEntry(n));
			}
		}
	}
	
	private void loadVacancyListProperties(Node node) throws Exception {
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				vacancyListProperties.add(loadEntry(n));
			}
		}
	}
	
	private void loadVacancyPaginatorProperties(Node node) {
		//System.out.println("loadVacancyPaginatorProperties");
	}
	
	private void loadVacancyProperties(Node node) throws Exception {
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
			    vacancyProperties.add(loadEntry(n));
			}
		}
	}
	
	private Entry loadEntry(Node node) throws Exception{
	    ClassLoader classLoader = App.class.getClassLoader();
		Entry result;
		String key="";
		String cssQuery="";
		List<CSSQueryArg> cssQueryArgs=new ArrayList<CSSQueryArg>();
		ElementWalker elementWalker=null;
		ElementEvaluator elementEvaluator=null;
		PropertyTransformer propertyTransformer=null;
		NodeList nodes=node.getChildNodes();
		
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				String nodeName=n.getNodeName();
				if ("key".equals(nodeName)){
					key=n.getTextContent();
				}else if ("cssQuery".equals(nodeName)){
					NamedNodeMap attributes = n.getAttributes();
					Node namedQueryAttributeNode=attributes.getNamedItem("namedQuery");
					Set<String> namedQueriesNamesSet=namedQueries.keySet();
					if (namedQueryAttributeNode!=null){
						String namedQueryName=namedQueryAttributeNode.getNodeValue();
						if (!namedQueriesNamesSet.contains(namedQueryName)){
							throw new Exception(String.format("Cannot find nnamed query '%s'",namedQueryName));
						}else{
							cssQuery=namedQueries.get(namedQueryName).getCssQuery();
						}
					}else{
						cssQuery=n.getTextContent();
					}
				}else if ("cssQueryArgs".equals(nodeName)){
					cssQueryArgs=loadCSSQueryArgs(n);
				}else if ("elementWalker".equals(nodeName)){
					String elementWalkerClassName=n.getTextContent();
					try {
				        @SuppressWarnings("rawtypes")
						Class aClass = classLoader.loadClass(elementWalkerClassName);
				        elementWalker = (ElementWalker) aClass.newInstance();
				    } catch (ClassNotFoundException e) {
				        throw new Exception(String.format("Cannot find element walker class %s",elementWalkerClassName));
				    } 
				}else if ("elementEvaluator".equals(nodeName)){
					String elementEvaluatorClassName = n.getTextContent();
					try{
						@SuppressWarnings("rawtypes")
						Class aClass = classLoader.loadClass(elementEvaluatorClassName);
						elementEvaluator = (ElementEvaluator) aClass.newInstance();
					}catch (ClassNotFoundException e) {
						throw new Exception(String.format("Cannot find element evaluator class %s",elementEvaluatorClassName));
				    } 
				}else if ("propertyTransformer".equals(nodeName)){
					String propertyTransformerClassName=n.getTextContent();
					try {
						@SuppressWarnings("rawtypes")
				        Class aClass = classLoader.loadClass(propertyTransformerClassName);
				        propertyTransformer=(PropertyTransformer) aClass.newInstance();
				    } catch (ClassNotFoundException e) {
				        throw new Exception(String.format("Cannot find property transformer class %s", propertyTransformerClassName));
				    } 
				}
			}
		}
		
		result=new Entry(key, cssQuery, cssQueryArgs, elementWalker, elementEvaluator, propertyTransformer);
		return result;
	}
	
	private List<CSSQueryArg> loadCSSQueryArgs(Node node){
		List<CSSQueryArg> result;
		result=new ArrayList<CSSQueryArg>();
		
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				CSSQueryArg queryArg=new CSSQueryArg(n.getTextContent());
				result.add(queryArg);
			}
		}
		
		return result;
	}

	//------------
	public Map<String, NamedCSSQuery> getNamedQueries() {
		return namedQueries;
	}

	public void setNamedQueries(Map<String, NamedCSSQuery> namedQueries) {
		this.namedQueries = namedQueries;
	}

	public List<Entry> getVacancySectionProperties() {
		return vacancySectionProperties;
	}

	public void setVacancySectionProperties(
			List<Entry> vacancySectionProperties) {
		this.vacancySectionProperties = vacancySectionProperties;
	}

	public List<Entry> getVacancyListProperties() {
		return vacancyListProperties;
	}

	public void setVacancyListProperties(
			List<Entry> vacancyListProperties) {
		this.vacancyListProperties = vacancyListProperties;
	}

	public List<Entry> getVacancyProperties() {
		return vacancyProperties;
	}

	public void setVacancyProperties(List<Entry> vacancyProperties) {
		this.vacancyProperties = vacancyProperties;
	}
	
	//---------
	
}
