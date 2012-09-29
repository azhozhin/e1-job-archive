package ru.xrm.app.transformers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JobDatePropertyTransformer implements PropertyTransformer {

	private final static List<String> months=new ArrayList<String>(
			Arrays.asList("января",
					"февраля",
					"марта",
					"апреля",
					"мая",
					"июня",
					"июля",
					"августа",
					"сентября",
					"октября",
					"ноября",
					"декабря"));

	public Object transform(String from) {
		// 28 сентября 2012
		String fromstr=(String)from;
		String []parts=fromstr.split(" ");
		Date result;
		int day=Integer.valueOf(parts[0]).intValue();
		int month=months.indexOf(parts[1]);
		int year=Integer.valueOf(parts[2]).intValue();
		result=new Date(day,month,year);
		return result;
	}

}
