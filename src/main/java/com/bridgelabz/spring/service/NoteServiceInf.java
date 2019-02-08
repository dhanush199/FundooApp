package com.bridgelabz.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.spring.model.Label;
import com.bridgelabz.spring.model.Note;

public interface NoteServiceInf {

	boolean createNote(int id,Note user, HttpServletRequest request);

	Note updateNote(int id,Note user,HttpServletRequest request);

	Note  deleteNote(int id, HttpServletRequest request);

	List<Note> retrieve(int userId,HttpServletRequest request);

	boolean createLabel(int id,Label label, HttpServletRequest request);

	Label  deleteLabel(int id, HttpServletRequest request);
	
	Label editLabel(int id,Label label,HttpServletRequest req);
	
	List<Label> retrieveLabel(int id,HttpServletRequest request);
	
	boolean mapNoteToLabel(String token, int noteId, int labelId, HttpServletRequest request);
	
	boolean removeNoteLabel(String token, int noteId, int labelId, HttpServletRequest request);

}




