package com.bridgelabz.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.spring.model.Label;
import com.bridgelabz.spring.model.Note;
import com.bridgelabz.spring.service.NoteServiceInf;
import com.bridgelabz.spring.utility.TokenGeneratorInf;

@RestController
public class NoteController {

	@Autowired
	private NoteServiceInf noteService;

	@Autowired
	private TokenGeneratorInf tokenGenerator;

	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<?> createNote(@RequestHeader ("token")String token,@RequestBody Note note, HttpServletRequest request,HttpServletResponse response) {
		int userId=tokenGenerator.authenticateToken(token);
		if (noteService.createNote(userId,note, request))
			return new ResponseEntity<String>("Note Succesfully Created",HttpStatus.OK);
		else
			return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/updatenote", method = RequestMethod.PUT)
	public ResponseEntity<String> updateNote(@RequestHeader("token") String token,@RequestBody Note user, HttpServletRequest request)
	{
		int userId=tokenGenerator.authenticateToken(token);
		if (noteService.updateNote(userId,user, request)!=null)
			return new ResponseEntity<String>("Note Succesfully updated",HttpStatus.OK);
		else
			return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@RequestMapping(value="/deletenote",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteNote(@RequestHeader("token") String token,HttpServletRequest request)
	{
		int userId=tokenGenerator.authenticateToken(token);
		try {
			if (noteService.deleteNote(userId,request)!=null)
				return new ResponseEntity<String>("Note Succesfully deleted",HttpStatus.FOUND);
			else
				return  new ResponseEntity<String>("Note not Found by given  Id",HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/retrievenote", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveNote(@RequestHeader("token") String token,HttpServletRequest request) {

		int userId=tokenGenerator.authenticateToken(token);
		List<Note> listOfNote = noteService.retrieve(userId,request);
		if (!listOfNote.isEmpty()) {
			return new ResponseEntity<List<Note>>(listOfNote, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("invalid user ID",HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/createlabel", method = RequestMethod.POST)

	public ResponseEntity<String> createLabel(@RequestHeader("token") String token,@RequestBody Label label, HttpServletRequest request) {
		try {
			int userId=tokenGenerator.authenticateToken(token);
			if(noteService.createLabel(userId, label, request))
				return new ResponseEntity<String>("Label Succesfully Created",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Details are not proper",HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("pls provide the proper LabelName",HttpStatus.CONFLICT);
	}

	@RequestMapping(value="/deletelabel",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteLabel(@RequestHeader ("token") String token,HttpServletRequest request)
	{
		int userId=tokenGenerator.authenticateToken(token);
		if (noteService.deleteLabel(userId,request)!=null)
			return new ResponseEntity<String>("label Succesfully deleted",HttpStatus.FOUND);
		else
			return  new ResponseEntity<String>("label not Found by given  Id",HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/editlabel", method = RequestMethod.PUT)
	public ResponseEntity<String> editLabel(@RequestHeader("token") String token,@RequestBody Label label, HttpServletRequest request)
	{
		int userId=tokenGenerator.authenticateToken(token);
		if (noteService.editLabel(userId, label,request)!=null)
			return new ResponseEntity<String>("Note Succesfully updated",HttpStatus.OK);
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/retrievelabel", method = RequestMethod.GET)
	public ResponseEntity<?> retriveLabel(@RequestHeader("token") String token,HttpServletRequest request) {
		int userId=tokenGenerator.authenticateToken(token);
		List<Label> listOfNote = noteService.retrieveLabel(userId,request);
		if (!listOfNote.isEmpty()) {
			return new ResponseEntity<List<Label>>(listOfNote, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("invalid user ID",HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/mergelabel&note", method = RequestMethod.POST)
	public ResponseEntity<String> mapNoteToLabel(@RequestHeader("token") String token,@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId, HttpServletRequest request)
	{
		if (noteService.mapNoteToLabel(token,noteId,labelId, request))
			return new ResponseEntity<String>("Mapped Succesfully",HttpStatus.OK);
		else
			return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/deletenote&label", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNoteLabel(@RequestHeader("token") String token,@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId, HttpServletRequest request) {
		if(noteService.removeNoteLabel(token,noteId,labelId,request))
			return new ResponseEntity<String>("Note and label has been successfully deleted",HttpStatus.NOT_FOUND);
		else 
			return new ResponseEntity<String>("invalid note/label ID",HttpStatus.NOT_FOUND);
	}

}