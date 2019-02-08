package com.bridgelabz.spring.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.utility.EmailUtil;
import com.bridgelabz.spring.utility.TokenGeneratorInf;

@Repository
public class UserDaoImpl implements UserDaoInf {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private TokenGeneratorInf tokenGenerator;

	@Autowired
	private EmailUtil email;

	public int register(User user) {
		int userId = 0;
		Session session = sessionFactory.getCurrentSession();
		userId = (Integer) session.save(user);
		return userId;
	}

	public User loginUser(User user,HttpServletRequest req,HttpServletResponse resp){

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where emailId= :emailId ");
		query.setString("emailId", user.getEmailId());
		//		query.setString("password", password);
		User existingUser = (User) query.uniqueResult();
		if (existingUser != null ) {
			if(existingUser.isActivationStatus()==true) {
				System.out.println("User detail is=" + existingUser.getId() + "," + existingUser.getName() + "," + existingUser.getEmailId() + ","
						+ existingUser.getMobileNumber());
				String token = tokenGenerator.generateToken(String.valueOf(existingUser.getId()));
				resp.setHeader("userId", token);
				session.close();
				return existingUser;
			}
			else {	
					StringBuffer url=req.getRequestURL();
					String url2=url.substring(0, url.lastIndexOf("/"));
					
				String token = tokenGenerator.generateToken(String.valueOf(user.getId()));
				url2=url2+"/verify/"+token;
				resp.setHeader("userId", token);
				email.sendEmail("", "Verification Mail", url2);
				return null;
			}

		}
		return null;
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
			session.close();
			return null;
		}
	}

	public void updateUser(int id, User user) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tr=session.beginTransaction();
		session.update(user);
		tr.commit();
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