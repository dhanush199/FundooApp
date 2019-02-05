package  com.bridgelabz.spring.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.spring.dao.NoteDao;
import com.bridgelabz.spring.model.Note;

@Service

public class NoteServiceImpl implements NoteServiceInf {

   
    @Autowired
     private NoteDao noteDao;
   
    @Transactional
    public boolean createNote(Note user, HttpServletRequest request) {
        int id = noteDao.createNote(user);
        if (id > 0) {
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
        public List<Note> retrieve(HttpServletRequest request) {
            List<Note> listOfNote = noteDao.retrieve();
            if (!listOfNote.isEmpty()) {
                return listOfNote;
            }
            return null;
        }
}