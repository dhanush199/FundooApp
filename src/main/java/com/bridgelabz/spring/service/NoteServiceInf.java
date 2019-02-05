package com.bridgelabz.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.spring.model.Note;

	public interface NoteService {
		   
	    boolean createNote(Note user, HttpServletRequest request);
	   
	    Note updateNote(int id,Note user,HttpServletRequest request);
	   
	    Note  deleteNote(int id, HttpServletRequest request);
	   
	   List<Note> retrieve(HttpServletRequest request);
	}




