package ru.xrm.app.config;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.jsoup.nodes.Element;
import org.junit.Test;

import ru.xrm.app.evaluators.ElementEvaluator;
import ru.xrm.app.transformers.PropertyTransformer;
import ru.xrm.app.walkers.ElementWalker;

public class EntryTest extends TestCase {

	@Test
	public void testEntryCreation() {
		
		Entry e=new Entry("", "", null, null, null, null);
		assertNotNull(e);
		assertEquals("",e.getKey());
		assertEquals("", e.getCssQuery());
		assertNull(e.getCssQueryArgs());
		assertNull(e.getElementEvaluator());
		assertNull(e.getElementWalker());
		assertNull(e.getPropertyTransformer());
	}
	
	@Test
	public void testSetters(){
		Entry e=new Entry("key", "query", null, null, null, null);
		e.setKey("name");
		assertEquals("name", e.getKey());
		final String QUERY_SIMPLE="here is simple query";
		e.setCssQuery(QUERY_SIMPLE);
		assertEquals(QUERY_SIMPLE, e.getCssQuery());
		
		final String QUERY_WITH_2_ARGS="here is query with two arguments %s %s"; 
		e.setCssQuery(QUERY_WITH_2_ARGS);
		assertEquals(QUERY_WITH_2_ARGS, e.getCssQuery());
		
		List<CSSQueryArg> args=new ArrayList<CSSQueryArg>();
		args.add(new CSSQueryArg("arg0"));
		args.add(new CSSQueryArg("arg1"));
		
		e.setCssQueryArgs(args);
		assertEquals(args, e.getCssQueryArgs());
		
		assertEquals(String.format(QUERY_WITH_2_ARGS, args.toArray()), e.getCssQuery());
		
		args=new ArrayList<CSSQueryArg>();
		e.setCssQueryArgs(args);
		assertEquals(QUERY_WITH_2_ARGS, e.getCssQuery());
		
		e.setElementWalker(new ElementWalker() {			
			public Element walk(Element element) {
				return null;
			}
		});
		assertNotNull(e.getElementWalker());
		
		e.setElementEvaluator(new ElementEvaluator() {
			public String evaluate(Element element) {
				return null;
			}
		});
		
		assertNotNull(e.getElementEvaluator());
		
		e.setPropertyTransformer(new PropertyTransformer() {
			public Object transform(String from) {
				return null;
			}
		});
		
		assertNotNull(e.getPropertyTransformer());
	}
}
