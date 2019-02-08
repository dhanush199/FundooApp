package com.bridgelabz.spring.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.bridgelabz.spring.model.Label;
import com.bridgelabz.spring.model.Note;

@Repository
public class NoteDaoImpl implements NoteDaoInf{

	@Autowired
	private SessionFactory sessionFactory;

	public boolean createNote(Note user) {
		int isNoteCreated = 0;
		Session session = sessionFactory.getCurrentSession();
		isNoteCreated = (Integer) session.save(user);
		if(isNoteCreated>0)
			return true;
		return false;
	}

	public Note updateNote(int id, Note user) {
		Session session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		session.update(user);
		tx.commit();
		session.close();
		return user;
	}

	public Note getNoteByID(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query  query = session.createQuery("from Note where id= :id");
		query.setInteger("id", id);
		Note emp = (Note) query.uniqueResult();
		if(user!=null) {
			System.out.println("Note details is="+ emp.getTitle() + emp.getDiscription()  +emp.getCreatedTime()+ emp.getUpdateTime() );
			tx.commit();
			session.close();
		}
		session.close();
		return emp;
	}

	public void deleteNote(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("DELETE from Note u where u.noteId= :noteId");
		query.setInteger("noteId", id);
		query.executeUpdate();
		tx.commit();
		session.close();
	}

	public List<Note> retrieve(int user_Id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Note where user_Id= :user_Id");
		query.setInteger("user_Id", user_Id);
		@SuppressWarnings("unchecked")
		List<Note> listOfNote = query.list();
		return listOfNote;
	}
	public int createLabel(Label label) {
		int labelId = 0;
		Session session = sessionFactory.getCurrentSession();
		labelId = (Integer) session.save(label);
		return labelId;
	}

	public Label getLabelByID(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query  query = session.createQuery("from Label where id= :id");
		query.setInteger("id", id);
		Label label = (Label) query.uniqueResult();
		if(user!=null) {
			System.out.println("Label details is="+ label.getLabelId() + label.getLabelName() );
			tx.commit();
			session.close();
		}
		session.close();
		return label;
	}
	public void deleteLabel(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("DELETE from Label u where u.labelId= :labelId");
		query.setInteger("labelId", id);
		query.executeUpdate();
		tx.commit();
		session.close();
	}
	public Label editLabel(int id, Label label) {
		Session session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		session.update(label);
		tx.commit();
		session.close();
		return label;
	}
	public List<Label> retrieveLabel(int id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Label where userId= :userId");
		query.setInteger("userId", id);
		@SuppressWarnings("unchecked")
		List<Label> listOfNote = query.list();
		return listOfNote;
	}

}