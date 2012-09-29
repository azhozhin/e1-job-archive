package ru.xrm.app.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.xrm.app.App;
import ru.xrm.app.evaluators.ElementEvaluator;
import ru.xrm.app.transformers.PropertyTransformer;
import ru.xrm.app.walkers.ElementWalker;

public class ConfigHelper {

	public static Entry loadEntry(Node node, Map<String,NamedCSSQuery> namedQueries) throws Exception{
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
	
	public static List<CSSQueryArg> loadCSSQueryArgs(Node node){
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
	
	public static Map<String,NamedCSSQuery> loadCSSNamedQueries(Node node) {
		Map<String,NamedCSSQuery> namedQueries=new HashMap<String,NamedCSSQuery>();
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
		return namedQueries;
	}
	
}
