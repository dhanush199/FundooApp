package com.bridgelabz.spring.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bridgelabz.spring.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public int register(User user) {
		int userId = 0;
		Session session = sessionFactory.getCurrentSession();
		userId = (Integer) session.save(user);
		return userId;
	}

	public User loginUser(String emailId, String password) {

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where emailId= :emailId and password =:password");
		query.setString("emailId", emailId);
		query.setString("password", password);
		User user = (User) query.uniqueResult();
		if (user != null) {
			System.out.println("User detail is=" + user.getId() + "," + user.getName() + "," + user.getEmailId() + ","
					+ user.getMobileNumber());
			session.close();
			return user;
		} else {
			return null;
		}

	}

	public User getUserById(int id) {

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where id= :id");
		query.setInteger("id", id);
		User user = (User) query.uniqueResult();
		if (user != null) {
			System.out.println("User detail is=" + user.getId() + "," + user.getName() + "," + user.getEmailId() + ","
					+ user.getMobileNumber());
			session.close();
			return user;
		} else {
			return null;
		}
	}

	public void updateUser(int id, User user) {
		Session session = sessionFactory.openSession();
		session.update(user);
		session.close();
	}

	public void deleteUser(int id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("DELETE from User u where u.id= :id");
		query.setInteger("id", id);
		query.executeUpdate();
		session.close();
	}

}