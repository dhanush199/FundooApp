package com.bridgelabz.spring.dao;

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
public class UserDaoImpl implements UserDao {

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

	public User loginUser(String emailId,HttpServletResponse resp){

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where emailId= :emailId ");
		query.setString("emailId", emailId);
		//		query.setString("password", password);
		User user = (User) query.uniqueResult();
		if (user != null ) {
			if(user.isActivationStatus()==true) {
				System.out.println("User detail is=" + user.getId() + "," + user.getName() + "," + user.getEmailId() + ","
						+ user.getMobileNumber());
				String token = tokenGenerator.generateToken(String.valueOf(user.getId()));
				resp.setHeader("userId", token);
				session.close();
				return user;
			}
			else {	
				//	StringBuffer url=request.getRequestURL();
				//	String url2=url.substring(0, url.lastIndexOf("/"));
				//	url2=url2+"/verify/"+token;
				String token = tokenGenerator.generateToken(String.valueOf(user.getId()));
				resp.setHeader("userId", token);
				email.sendEmail("", "Verification Mail", "http://localhost:8080/FundooNote/verify/"+token);
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