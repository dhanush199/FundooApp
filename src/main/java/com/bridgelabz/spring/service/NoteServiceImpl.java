package  com.bridgelabz.spring.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.spring.dao.NoteDao;
import com.bridgelabz.spring.dao.UserDao;
import com.bridgelabz.spring.model.Label;
import com.bridgelabz.spring.model.Note;
import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.utility.TokenGeneratorInf;

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
	@Transactional
	public boolean createLabel(int id,Label label, HttpServletRequest request){
		User user1=userDao.getUserById(id);
		if(user1 !=null) {
		label.setUserId(user1);
		int id1 = noteDao.createLabel(label);
		if (id1 > 0) {
			return true;
		}}
		return false;
	}
	
	@Transactional
	public Label  deleteLabel(int id, HttpServletRequest request) {
		Label user12=noteDao.getLabelByID(id);
		noteDao.deleteLabel(id);
		return user12;
	}
	@Transactional
	public Label editLabel(int id,Label label,HttpServletRequest req)
	{
		Label label1=noteDao.getLabelByID(id);
		if(label1!=null) {
			label1.setLabelName(label.getLabelName());
			noteDao.editLabel(id, label1);
		}
		return label1;}
	@Transactional
	public List<Label> retrieveLabel(int id,HttpServletRequest request) {
		List<Label> listOfNote = noteDao.retrieveLabel(id);
		if (!listOfNote.isEmpty()) {
			return listOfNote;
		}
		return null;
	}
	@Autowired
	private TokenGeneratorInf tokenGenerator;
	@Transactional
    public boolean mapNoteToLabel(String token, int noteId, int labelId, HttpServletRequest request) {
        int id = tokenGenerator.authenticateToken(token);
        User user = userDao.getUserById(id);
        if (user != null) {
            Note note = noteDao.getNoteByID(noteId);
            Label label = noteDao.getLabelByID(labelId);
            List<Label> listOfLabel = note.getLabelList();
            listOfLabel.add(label);
            if (!listOfLabel.isEmpty()) {
                note.setLabelList(listOfLabel);
                noteDao.updateNote(1, note);
                return true;
            }
        }
        return false;
    }
}