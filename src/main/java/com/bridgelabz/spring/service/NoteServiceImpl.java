package  com.bridgelabz.spring.service;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.spring.dao.NoteDaoInf;
import com.bridgelabz.spring.dao.UserDaoInf;
import com.bridgelabz.spring.model.Label;
import com.bridgelabz.spring.model.Note;
import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.utility.TokenGeneratorInf;

@Service
public class NoteServiceImpl implements NoteServiceInf {

	@Autowired
	private NoteDaoInf noteDaoInf;

	@Autowired
	private UserDaoInf userDaoInf;

	@Autowired
	private TokenGeneratorInf tokenGenerator;

	@Transactional
	public boolean createNote(int id,Note note, HttpServletRequest request) {
		User existingUser=userDaoInf.getUserById(id);
		boolean isNoteCreated = noteDaoInf.createNote(note);
		if (isNoteCreated) {
			note.setUser_Id(existingUser);
			return true;
		}
		return false;
	}

	@Transactional
	public Note updateNote(int id,Note user,HttpServletRequest request)
	{
		Note aliveNote=noteDaoInf.getNoteByID(id);
		if(aliveNote!=null) {
			aliveNote.setTitle(user.getTitle());
			aliveNote.setDiscription(user.getDiscription());
			noteDaoInf.updateNote(id, aliveNote);
		}
		return aliveNote;}

	@Transactional
	public Note deleteNote(int id,HttpServletRequest request) {
		Note aliveUser=noteDaoInf.getNoteByID(id);
		noteDaoInf.deleteNote(id);
		return aliveUser;
	}

	@Transactional
	public List<Note> retrieve(int id,HttpServletRequest request) {
		List<Note> Notes = noteDaoInf.retrieve(id);
		if (!Notes.isEmpty()) {
			return Notes;
		}
		return null;
	}
	@Transactional
	public boolean createLabel(int id,Label label, HttpServletRequest request){
		User residingUser=userDaoInf.getUserById(id);
		if(residingUser !=null) {
			label.setUserId(residingUser);
			int id1 = noteDaoInf.createLabel(label);
			if (id1 > 0) {
				return true;
			}}
		return false;
	}

	@Transactional
	public Label deleteLabel(int id, HttpServletRequest request) {
		Label aliveLabel=noteDaoInf.getLabelByID(id);
		noteDaoInf.deleteLabel(id);
		return aliveLabel;
	}
	@Transactional
	public Label editLabel(int id,Label label,HttpServletRequest req)
	{
		Label aliveLabel=noteDaoInf.getLabelByID(id);
		if(aliveLabel!=null) {
			aliveLabel.setLabelName(label.getLabelName());
			noteDaoInf.editLabel(id, aliveLabel);
		}
		return aliveLabel;}
	@Transactional
	public List<Label> retrieveLabel(int id,HttpServletRequest request) {
		List<Label> notes = noteDaoInf.retrieveLabel(id);
		if (!notes.isEmpty()) {
			return notes;
		}
		return null;
	}

	@Transactional
	public boolean mapNoteToLabel(String token, int noteId, int labelId, HttpServletRequest request) {
		int id = tokenGenerator.authenticateToken(token);
		User existingUser = userDaoInf.getUserById(id);
		if (existingUser != null) {
			Note note = noteDaoInf.getNoteByID(noteId);
			Label label = noteDaoInf.getLabelByID(labelId);
			List<Label> labels = note.getLabelList();
			labels.add(label);
			if (!labels.isEmpty()) {
				note.setLabelList(labels);
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
			Note residingNote = noteDaoInf.getNoteByID(noteId);
			List<Label> labels = residingNote.getLabelList();
			if (!labels.isEmpty()) {
				labels = labels.stream().filter(label -> label.getLabelId() != labelId)
						.collect(Collectors.toList());
				residingNote.setLabelList(labels);
				noteDaoInf.updateNote(noteId, residingNote);
				return true;
			}
		}
		return false;
	}

}