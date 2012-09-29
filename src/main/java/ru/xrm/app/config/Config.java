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
			System.out.format("%s \n",n.getNodeName());
			loadProperties(n);
		}
	}
	
	private void loadProperties(Node node){
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				//System.out.format("\t %s\n", n.getNodeName());
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
		//System.out.println("loadCSSNamedQueries");
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				NamedNodeMap attributes=n.getAttributes();
				String queryName=attributes.getNamedItem("name").getNodeValue();
				//System.out.format("NAMEDQUERY %s \n", queryName);
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
				//System.out.format("NAMEDQUERY CSSQUERY %s ARGS %s\n", cssQuery, cssArgsCount);
				namedQueries.put(queryName,new NamedCSSQuery(queryName,cssQuery, cssArgsCount));
			}
		}
	}
	
	private void loadVacancySectionProperties(Node node) {
		//System.out.println("loadSectionProperties");
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){				
				//System.out.format("VACANCY SECTION %s %s\n", key, cssQuery);
				vacancySectionProperties.add(loadEntry(n));
			}
		}
	}
	
	private void loadVacancyListProperties(Node node) {
		//System.out.println("loadVacancyListProperties");
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				//System.out.format("VACANCY SECTION %s %s\n", key, cssQuery);
				vacancyListProperties.add(loadEntry(n));
			}
		}
	}
	
	private void loadVacancyPaginatorProperties(Node node) {
		//System.out.println("loadVacancyPaginatorProperties");
	}
	
	private void loadVacancyProperties(Node node) {
		//System.out.println("loadVacancyProperties");
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				//System.out.println(n.getNodeName());
			    vacancyProperties.add(loadEntry(n));
			}
		}
	}
	
	private Entry loadEntry(Node node){
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
					//System.out.format(">>> %s=%s\n",n.getNodeName(),key);
				}else if ("cssQuery".equals(nodeName)){
					// if we have namedQuery attribute, then we should get it from namedQueries
					NamedNodeMap attributes = n.getAttributes();
					Node namedQueryAttributeNode=attributes.getNamedItem("namedQuery");
					Set<String> namedQueriesNamesSet=namedQueries.keySet();
					if (namedQueryAttributeNode!=null){
						String namedQueryName=namedQueryAttributeNode.getNodeValue();
						//System.out.format("*** named Query %s \n",namedQueryName);
						if (!namedQueriesNamesSet.contains(namedQueryName)){
							System.out.format("!!! cannot find named query %s \n",namedQueryName);
						}else{
							cssQuery=namedQueries.get(namedQueryName).getCssQuery();
						}
					}else{
						cssQuery=n.getTextContent();
					}
					//System.out.format(">>> %s=%s\n", n.getNodeName(),cssQuery);
				}else if ("cssQueryArgs".equals(nodeName)){
					cssQueryArgs=loadCSSQueryArgs(n);
					//System.out.format("QUERY ARGS:\n");
				}else if ("elementWalker".equals(nodeName)){
					String elementWalkerClassName=n.getTextContent();
					//System.out.format("ELEMENT WALKER %s\n", elementWalkerClassName);
					try {
				        Class aClass = classLoader.loadClass(elementWalkerClassName);
				        elementWalker = (ElementWalker) aClass.newInstance();
				        //System.out.println("aClass.getName() = " + aClass.getName());
				    } catch (ClassNotFoundException e) {
				        e.printStackTrace();
				    } catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}else if ("elementEvaluator".equals(nodeName)){
					String elementEvaluatorClassName = n.getTextContent();
					try{
						Class aClass = classLoader.loadClass(elementEvaluatorClassName);
						elementEvaluator = (ElementEvaluator) aClass.newInstance();
					}catch (ClassNotFoundException e) {
				        e.printStackTrace();
				    } catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}else if ("propertyTransformer".equals(nodeName)){
					String propertyTransformerClassName=n.getTextContent();
					//System.out.format("PROPERTY TRANSFORMER %s\n", propertyTransformerClassName);
					try {
				        Class aClass = classLoader.loadClass(propertyTransformerClassName);
				        propertyTransformer=(PropertyTransformer) aClass.newInstance();
				        //System.out.println("aClass.getName() = " + aClass.getName());
				    } catch (ClassNotFoundException e) {
				        e.printStackTrace();
				    } catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				//System.out.println(n.getNodeName());
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
