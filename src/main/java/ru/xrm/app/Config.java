package ru.xrm.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.bcel.internal.classfile.Attribute;

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
					loadSectionProperties(n);
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

	private void loadCSSNamedQueries(Node n) {
		System.out.println("loadCSSNamedQueries");
	}
	
	private void loadSectionProperties(Node n) {
		System.out.println("loadSectionProperties");
	}
	

	private void loadVacancyListProperties(Node n) {
		System.out.println("loadVacancyListProperties");
	}
	
	private void loadVacancyPaginatorProperties(Node n) {
		System.out.println("loadVacancyPaginatorProperties");
	}
	
	private void loadVacancyProperties(Node node) {
		System.out.println("loadVacancyProperties");
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				System.out.println(n.getNodeName());
			    vacancyProperties.add(loadVacancyProperty(n));
			}
		}
	}

	private VacancyProperty loadVacancyProperty(Node node) {
	    ClassLoader classLoader = App.class.getClassLoader();

		VacancyProperty result=null;
		String key="";
		String cssQuery="";
		List<CSSQueryArg> cssQueryArgs;
		ElementWalker elementWalker;
		PropertyTransformer propertyTransformer;

		NodeList nodes=node.getChildNodes();
		
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				String nodeName=n.getNodeName();
				if (nodeName.equals("key")){
					key=n.getTextContent();
					System.out.format(">>> %s=%s\n",n.getNodeName(),key);
				}else if (nodeName.equals("cssQuery")){
					// if we have namedQuery attribute, then we should get it from namedQueries
					NamedNodeMap attributes = n.getAttributes();
					Node namedQueryAttributeNode=attributes.getNamedItem("namedQuery");
					if (namedQueryAttributeNode!=null){
						String namedQueryName=namedQueryAttributeNode.getNodeValue();
						System.out.format("*** named Query %s \n",namedQueryName);
						if (namedQueries.indexOf(namedQueryName)<0){
							System.out.format("!!! cannot find named query %s \n",namedQueryName);
						}else{
							cssQuery=namedQueries.get(namedQueries.indexOf(namedQueryName)).getQuery();
						}
					}else{
						cssQuery=n.getTextContent();
					}
					System.out.format(">>> %s=%s\n", n.getNodeName(),cssQuery);
				}else if (nodeName.equals("cssQueryArgs")){
					cssQueryArgs=loadCSSQueryArgs(n);
					System.out.format("QUERY ARGS:\n");
					for (CSSQueryArg arg:cssQueryArgs){
						System.out.format("&&& %s\n",arg.getArg());
					}
				}else if (nodeName.equals("elementWalker")){
					String elementWalkerClassName=n.getTextContent();
					System.out.format("ELEMENT WALKER %s\n", elementWalkerClassName);
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
					System.out.format("PROPERTY TRANSFORMER %s\n", propertyTransformerClassName);
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
		
		//result=new VacancyProperty(key, cssQuery, cssQueryArgs, elementWalker, propertyTransformer);
		
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
