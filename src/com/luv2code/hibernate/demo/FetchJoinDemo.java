package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class FetchJoinDemo {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(Course.class).addAnnotatedClass(InstructorDetail.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {

			int theId = 1;

			session.beginTransaction();

			Query<Instructor> query = session.createQuery(
					"select i from Instructor i " + "JOIN FETCH i.courses " + "where i.id=:theInstructorId",
					Instructor.class);

			query.setParameter("theInstructorId", theId);

			Instructor tempInstructor = query.getSingleResult();

			System.out.println("luv2code: Instructor: " + tempInstructor);

			session.getTransaction().commit();
			session.close();

			System.out.println("\nThe session is now closed!\n");

			System.out.println("luv2code: Courses: " + tempInstructor.getCourses());

			System.out.println("luv2code: Done!");

		} finally {
			session.close();
			factory.close();
		}
	}

}
