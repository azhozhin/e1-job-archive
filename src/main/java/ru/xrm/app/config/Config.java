package ru.xrm.app.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Config {

	private static Config instance;
	
	Map<String,NamedCSSQuery> namedQueries;
	List<Entry> vacancySectionProperties;
	List<Entry> vacancyListProperties;
	List<Entry> vacancyProperties;
	List<Entry> vacancyListPaginatorProperties;

	public static Config getInstance(){
		if (instance==null){
			instance=new Config();
		}
		return instance;
	}
	
	private Config(){
		namedQueries=new HashMap<String,NamedCSSQuery>();
		vacancySectionProperties=new ArrayList<Entry>();
		vacancyListProperties=new ArrayList<Entry>();
		vacancyProperties=new ArrayList<Entry>();
		vacancyListPaginatorProperties=new ArrayList<Entry>();
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
					namedQueries=ConfigHelper.loadCSSNamedQueries(n);
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

	private void loadVacancySectionProperties(Node node) throws Exception {
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){				
				vacancySectionProperties.add(ConfigHelper.loadEntry(n,namedQueries));
			}
		}
	}
	
	private void loadVacancyListProperties(Node node) throws Exception {
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				vacancyListProperties.add(ConfigHelper.loadEntry(n,namedQueries));
			}
		}
	}
	
	private void loadVacancyPaginatorProperties(Node node) throws Exception {
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
				vacancyListPaginatorProperties.add(ConfigHelper.loadEntry(n,namedQueries));
			}
		}
	}
	
	private void loadVacancyProperties(Node node) throws Exception {
		NodeList nodes = node.getChildNodes();
		for (int i=0;i<nodes.getLength();i++){
			Node n=nodes.item(i);
			if (n.getNodeType()==Node.ELEMENT_NODE){
			    vacancyProperties.add(ConfigHelper.loadEntry(n,namedQueries));
			}
		}
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

	public List<Entry> getVacancyListPaginatorProperties() {
		return vacancyListPaginatorProperties;
	}

	public void setVacancyListPaginatorProperties(
			List<Entry> vacancyListPaginatorProperties) {
		this.vacancyListPaginatorProperties = vacancyListPaginatorProperties;
	}
	
	//---------
	
}
