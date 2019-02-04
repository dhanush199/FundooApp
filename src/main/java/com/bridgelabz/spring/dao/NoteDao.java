package com.bridgelabz.spring.dao;

import java.util.List;

import com.bridgelabz.spring.model.Note;
import com.bridgelabz.spring.model.User;

public interface NoteDao {

	Note getNoteByID(int id);

	void updateNote(int id, Note user12);

	void deleteNote(int id);

	List<Note> retrieve();

	int createNote(Note user);

}
