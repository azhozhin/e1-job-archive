package ru.xrm.app;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.util.HibernateUtil;

public class Try {
/*
	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			Section section = new Section("Section 1", "http://ya.ru");
			section.setId(new Long(1));

			Vacancy vacancy1 = new Vacancy();
			vacancy1.setId(new Long(1));
			vacancy1.setSalary(new Integer(100));
			vacancy1.setJobTitle("worker");
			vacancy1.setDutyType("full");
			vacancy1.setEducation("educ");
			vacancy1.setExperience(" ");
			vacancy1.setSchedule("any");
			vacancy1.setCity("moscow");
			vacancy1.setEmployer("empl");
			vacancy1.setDate(new Date(2012, 8, 1));
			vacancy1.setContactInformation("fhgf");
			vacancy1.setPresentedBy("me");

			vacancy1.setBody("text");

			vacancy1.setSection(section);

			// System.out.print(vacancy1);

			session.save(section);
			session.save(vacancy1);

			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	*/
}
