package com.bridgelabz.spring.dao;

import java.util.List;

import com.bridgelabz.spring.model.Note;

public interface NoteDao {

	Object user = null;

	int createNote(Note user);

	Note getNoteByID(int id);

	Note updateNote(int id, Note user12);

	void deleteNote(int id);

	List<Note> retrieve(int id);

}