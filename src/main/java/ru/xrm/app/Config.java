package ru.xrm.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import ru.xrm.app.transformers.PropertyTransformer;
import ru.xrm.app.walkers.ElementWalker;


public class Config {

	List<NamedCSSQuery> namedQueries;
	List<VacancySectionProperty> vacancySectionProperties;
	List<VacancyListProperty> vacancyListProperties;
	List<VacancyProperty> vacancyProperties;
	
	public Config(){
		namedQueries=new ArrayList<NamedCSSQuery>();
		vacancySectionProperties=new ArrayList<VacancySectionProperty>();
		vacancyListProperties=new ArrayList<VacancyListProperty>();
		vacancyProperties=new ArrayList<VacancyProperty>();
	}
	
	public void load(String filename) throws Exception{
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
				System.out.format("\t %s\n", n.getNodeName());
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
		System.out.println("loadCSSNamedQueries");
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
				namedQueries.add(new NamedCSSQuery(queryName, cssQuery, cssArgsCount));
			}
		}
	}
	
	private void loadVacancySectionProperties(Node node) {
		//System.out.println("loadSectionProperties");
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				NodeList entries=n.getChildNodes();
				String key="";
				String cssQuery="";
				for (int j=0;j<entries.getLength();j++){
					Node entry=entries.item(j);
					if (entry.getNodeType()==node.ELEMENT_NODE){
						if ("key".equals(entry.getNodeName())){
							key=entry.getTextContent();
						}else if ("cssQuery".equals(entry.getNodeName())){
							cssQuery=entry.getTextContent();
						}
					}
				}
				//System.out.format("VACANCY SECTION %s %s\n", key, cssQuery);
				vacancySectionProperties.add(new VacancySectionProperty(key,cssQuery));
			}
		}
	}
	

	private void loadVacancyListProperties(Node node) {
		System.out.println("loadVacancyListProperties");
	}
	
	private void loadVacancyPaginatorProperties(Node node) {
		System.out.println("loadVacancyPaginatorProperties");
	}
	
	private void loadVacancyProperties(Node node) {
		//System.out.println("loadVacancyProperties");
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				//System.out.println(n.getNodeName());
			    vacancyProperties.add(loadVacancyProperty(n));
			}
		}
	}

	private VacancyProperty loadVacancyProperty(Node node) {
	    ClassLoader classLoader = App.class.getClassLoader();

		VacancyProperty result=null;
		String key="";
		String cssQuery="";
		List<CSSQueryArg> cssQueryArgs=null;
		ElementWalker elementWalker=null;
		PropertyTransformer propertyTransformer=null;

		NodeList nodes=node.getChildNodes();
		
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				String nodeName=n.getNodeName();
				if (nodeName.equals("key")){
					key=n.getTextContent();
					//System.out.format(">>> %s=%s\n",n.getNodeName(),key);
				}else if (nodeName.equals("cssQuery")){
					// if we have namedQuery attribute, then we should get it from namedQueries
					NamedNodeMap attributes = n.getAttributes();
					Node namedQueryAttributeNode=attributes.getNamedItem("namedQuery");
					if (namedQueryAttributeNode!=null){
						String namedQueryName=namedQueryAttributeNode.getNodeValue();
						//System.out.format("*** named Query %s \n",namedQueryName);
						if (namedQueries.indexOf(namedQueryName)<0){
							System.out.format("!!! cannot find named query %s \n",namedQueryName);
						}else{
							cssQuery=namedQueries.get(namedQueries.indexOf(namedQueryName)).getCssQuery();
						}
					}else{
						cssQuery=n.getTextContent();
					}
					//System.out.format(">>> %s=%s\n", n.getNodeName(),cssQuery);
				}else if (nodeName.equals("cssQueryArgs")){
					cssQueryArgs=loadCSSQueryArgs(n);
					//System.out.format("QUERY ARGS:\n");
					for (CSSQueryArg arg:cssQueryArgs){
						System.out.format("&&& %s\n",arg.getArg());
					}
				}else if (nodeName.equals("elementWalker")){
					String elementWalkerClassName=n.getTextContent();
					//System.out.format("ELEMENT WALKER %s\n", elementWalkerClassName);
					try {
				        Class aClass = classLoader.loadClass(elementWalkerClassName);
				        elementWalker = (ElementWalker) aClass.newInstance();
				        System.out.println("aClass.getName() = " + aClass.getName());
				    } catch (ClassNotFoundException e) {
				        e.printStackTrace();
				    } catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}else if (nodeName.equals("propertyTransformer")){
					String propertyTransformerClassName=n.getTextContent();
					//System.out.format("PROPERTY TRANSFORMER %s\n", propertyTransformerClassName);
					try {
				        Class aClass = classLoader.loadClass(propertyTransformerClassName);
				        propertyTransformer=(PropertyTransformer) aClass.newInstance();
				        System.out.println("aClass.getName() = " + aClass.getName());
				    } catch (ClassNotFoundException e) {
				        e.printStackTrace();
				    } catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				System.out.println(n.getNodeName());
			}
		}
		
		result=new VacancyProperty(key, cssQuery, cssQueryArgs, elementWalker, propertyTransformer);
		
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
}
