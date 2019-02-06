package  com.bridgelabz.spring.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.spring.dao.NoteDao;
import com.bridgelabz.spring.dao.UserDao;
import com.bridgelabz.spring.model.Note;
import com.bridgelabz.spring.model.User;

@Service
public class NoteServiceImpl implements NoteServiceInf {

	@Autowired
	private NoteDao noteDao;
	
	@Autowired
	private UserDao userDao;

	@Transactional
	public boolean createNote(int id,Note note, HttpServletRequest request) {
		User user1=userDao.getUserById(id);
		int id1 = noteDao.createNote(note);
		if (id1 > 0) {
			note.setUser_Id(user1);
			return true;
		}
		return false;
	}

	@Transactional
	public Note updateNote(int id,Note user,HttpServletRequest request)
	{
		Note user12=noteDao.getNoteByID(id);
		if(user12!=null) {
			user12.setTitle(user.getTitle());
			user12.setDiscription(user.getDiscription());
			noteDao.updateNote(id, user12);
		}
		return user12;}

	@Transactional
	public Note deleteNote(int id,HttpServletRequest request) {
		Note user12=noteDao.getNoteByID(id);
		noteDao.deleteNote(id);
		return user12;
	}

	@Transactional
	public List<Note> retrieve(int id,HttpServletRequest request) {
		List<Note> listOfNote = noteDao.retrieve(id);
		if (!listOfNote.isEmpty()) {
			return listOfNote;
		}
		return null;
	}
}