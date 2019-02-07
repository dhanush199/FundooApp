package com.bridgelabz.spring.dao;

import java.util.List;

import com.bridgelabz.spring.model.Label;
import com.bridgelabz.spring.model.Note;

public interface NoteDao {

	Object user = null;

	int createNote(Note user);

	Note getNoteByID(int id);
	
	Label getLabelByID(int id);

	Note updateNote(int id, Note user12);

	void deleteNote(int id);
	
	void deleteLabel(int id);
	
	List<Note> retrieve(int id);

	int createLabel(Label label);
	
	 Label editLabel(int id, Label label);
	 
	 List<Label> retrieveLabel(int id);
}