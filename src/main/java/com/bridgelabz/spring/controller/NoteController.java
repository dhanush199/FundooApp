package com.bridgelabz.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.spring.model.Note;
import com.bridgelabz.spring.service.NoteServiceInf;

@RestController
public class NoteController {

	@Autowired
	private NoteServiceInf noteService;

	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<?> registerNote(@RequestParam ("id") int id,@RequestBody Note note, HttpServletRequest request) {
		//try {
		if (noteService.createNote(id,note, request))
			return new ResponseEntity<String>("Note Succesfully Created",HttpStatus.OK);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//			return new ResponseEntity<String>("Details are not proper",HttpStatus.CONFLICT);
		//		}
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/updatenote", method = RequestMethod.POST)
	public ResponseEntity<String> updateNote(@RequestParam("id") int id,@RequestBody Note user, HttpServletRequest request)
	{
		try {
			if (noteService.updateNote(id,user, request)!=null)
				return new ResponseEntity<String>("Note Succesfully updated",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("User not Found by given  Id",HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}


	@RequestMapping(value="/deletenote",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteNote(@RequestParam("id") int id ,HttpServletRequest request)
	{
		try {
			if (noteService.deleteNote(id,request)!=null)
				return new ResponseEntity<String>("User Succesfully deleted",HttpStatus.FOUND);
			else
				return  new ResponseEntity<String>("User not Found by given  Id",HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}
	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	public ResponseEntity<?> createNote(@RequestParam("user_Id") int user_Id,HttpServletRequest request) {
		List<Note> listOfNote = noteService.retrieve(user_Id,request);
		if (!listOfNote.isEmpty()) {
			return new ResponseEntity<List<Note>>(listOfNote, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("invalid user ID",HttpStatus.NOT_FOUND);
		}
	}

}