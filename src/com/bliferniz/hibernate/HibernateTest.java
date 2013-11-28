/**
 * 
 */
package com.bliferniz.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.bliferniz.dto.UserDetails;

public class HibernateTest {

	private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	public static void main(String[] args) {

		getUserPerExample();
		getUserPerExampleWithLikeFunction();
		getAllUserIDsDESC();		

	}

	private static void getUserPerExampleWithLikeFunction() {
		Session session = factory.openSession();
		session.beginTransaction();
		
		UserDetails exampleUser = new UserDetails();
		exampleUser.setUserId(5); //Primary key will not be considered
		exampleUser.setUserName("User5%");

		Example example = Example.create(exampleUser).enableLike();
		
		
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(example);
		
		
		List<UserDetails> users = (List<UserDetails>) criteria.list();
		
		session.getTransaction().commit();
		session.close();

		for(UserDetails user : users){
			System.out.println(user.getUserId() + " " + user.getUserName());
		}
	}

	private static void getUserPerExample() {
		Session session = factory.openSession();
		session.beginTransaction();
		
		UserDetails exampleUser = new UserDetails();
		exampleUser.setUserId(5); //Primary key will not be considered
		exampleUser.setUserName("User5");

		Example example = Example.create(exampleUser).excludeProperty("userName");
		
		
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(example);
		
		
		List<UserDetails> users = (List<UserDetails>) criteria.list();
		
		session.getTransaction().commit();
		session.close();

		for(UserDetails user : users){
			System.out.println(user.getUserId() + " " + user.getUserName());
		}
	}

	private static void getAllUserIDsDESC() {
		Session session = factory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.setProjection(Projections.property("userId")).addOrder(Order.desc("userId"));
		
		List<Integer> userIds = (List<Integer>) criteria.list();
		
		session.getTransaction().commit();
		session.close();

		for(Integer userId : userIds){
			System.out.println(userId);
		}
	}

	
}
