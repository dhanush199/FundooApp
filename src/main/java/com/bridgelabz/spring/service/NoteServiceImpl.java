package  com.bridgelabz.spring.service;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.spring.dao.NoteDao;
import com.bridgelabz.spring.dao.UserDaoInf;
import com.bridgelabz.spring.model.Label;
import com.bridgelabz.spring.model.Note;
import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.utility.TokenGeneratorInf;

@Service
public class NoteServiceImpl implements NoteServiceInf {

	@Autowired
	private NoteDao noteDaoInf;

	@Autowired
	private UserDaoInf userDaoInf;
	
	@Autowired
	private TokenGeneratorInf tokenGenerator;

	@Transactional
	public boolean createNote(int id,Note note, HttpServletRequest request) {
		User user1=userDaoInf.getUserById(id);
		int id1 = noteDaoInf.createNote(note);
		if (id1 > 0) {
			note.setUser_Id(user1);
			return true;
		}
		return false;
	}

	@Transactional
	public Note updateNote(int id,Note user,HttpServletRequest request)
	{
		Note user12=noteDaoInf.getNoteByID(id);
		if(user12!=null) {
			user12.setTitle(user.getTitle());
			user12.setDiscription(user.getDiscription());
			noteDaoInf.updateNote(id, user12);
		}
		return user12;}

	@Transactional
	public Note deleteNote(int id,HttpServletRequest request) {
		Note user12=noteDaoInf.getNoteByID(id);
		noteDaoInf.deleteNote(id);
		return user12;
	}

	@Transactional
	public List<Note> retrieve(int id,HttpServletRequest request) {
		List<Note> listOfNote = noteDaoInf.retrieve(id);
//		Label label=noteDao.getLabelByID(id);
//		Note note=noteDao.getNoteByID(id);
		if (!listOfNote.isEmpty()) {
			return listOfNote;
		}
		return null;
	}
	@Transactional
	public boolean createLabel(int id,Label label, HttpServletRequest request){
		User user1=userDaoInf.getUserById(id);
		if(user1 !=null) {
			label.setUserId(user1);
			int id1 = noteDaoInf.createLabel(label);
			if (id1 > 0) {
				return true;
			}}
		return false;
	}

	@Transactional
	public Label  deleteLabel(int id, HttpServletRequest request) {
		Label user12=noteDaoInf.getLabelByID(id);
		noteDaoInf.deleteLabel(id);
		return user12;
	}
	@Transactional
	public Label editLabel(int id,Label label,HttpServletRequest req)
	{
		Label label1=noteDaoInf.getLabelByID(id);
		if(label1!=null) {
			label1.setLabelName(label.getLabelName());
			noteDaoInf.editLabel(id, label1);
		}
		return label1;}
	@Transactional
	public List<Label> retrieveLabel(int id,HttpServletRequest request) {
		List<Label> listOfNote = noteDaoInf.retrieveLabel(id);
		if (!listOfNote.isEmpty()) {
			return listOfNote;
		}
		return null;
	}

	@Transactional
	public boolean mapNoteToLabel(String token, int noteId, int labelId, HttpServletRequest request) {
		int id = tokenGenerator.authenticateToken(token);
		User user = userDaoInf.getUserById(id);
		if (user != null) {
			Note note = noteDaoInf.getNoteByID(noteId);
			Label label = noteDaoInf.getLabelByID(labelId);
			List<Label> listOfLabel = note.getLabelList();
			listOfLabel.add(label);
			if (!listOfLabel.isEmpty()) {
				note.setLabelList(listOfLabel);
				noteDaoInf.updateNote(1, note);
				return true;
			}
		}
		return false;
	}
	public boolean removeNoteLabel(String token, int noteId, int labelId, HttpServletRequest request) {
        int id = tokenGenerator.authenticateToken(token);
        User user = userDaoInf.getUserById(id);
        if (user != null) {
            Note note = noteDaoInf.getNoteByID(noteId);
            List<Label> listOfLabels = note.getLabelList();
            if (!listOfLabels.isEmpty()) {
                listOfLabels = listOfLabels.stream().filter(label -> label.getLabelId() != labelId)
                        .collect(Collectors.toList());
                note.setLabelList(listOfLabels);
                noteDaoInf.updateNote(noteId, note);
                return true;
            }
        }
        return false;
    }

}