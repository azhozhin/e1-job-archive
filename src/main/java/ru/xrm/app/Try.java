package ru.xrm.app;


import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.util.DAOUtil;


public class Try {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(Try.class);
		
		logger.info("Hello there");
		
		DAOUtil.getInstance().beginTransaction();
		//List<Vacancy> list=DAOUtil.getInstance().getVacancyDAO().findAll();
		List<Vacancy> list=DAOUtil.getInstance().getVacancyDAO().findMany(Restrictions.ilike("jobTitle", "%Опер%"));
		
		for (Vacancy v:list){
			System.out.format("%s \n",v.getJobTitle());
		}
		
		System.out.println(DAOUtil.getInstance().getVacancyDAO().countByCriterions(Restrictions.ilike("jobTitle", "%Опер%")));
		
		DAOUtil.getInstance().commitTransaction();
		
	}
}
