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

@RestController
public class NoteController {

	@Autowired
	private NoteServiceInf noteService;

	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<?> createNote(@RequestHeader ("token") String token,@RequestParam ("id") int user_Id,@RequestBody Note note, HttpServletRequest request,HttpServletResponse response) {
		//try {
		response.setHeader("token", token);
		if (noteService.createNote(user_Id,note, request))
			return new ResponseEntity<String>("Note Succesfully Created",HttpStatus.OK);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//			return new ResponseEntity<String>("Details are not proper",HttpStatus.CONFLICT);
		//		}
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/updatenote", method = RequestMethod.PUT)
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
	@RequestMapping(value = "/retrievenote", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveNote(@RequestParam("user_Id") int user_Id,HttpServletRequest request) {
		List<Note> listOfNote = noteService.retrieve(user_Id,request);
		if (!listOfNote.isEmpty()) {
			return new ResponseEntity<List<Note>>(listOfNote, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("invalid user ID",HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/createlabel", method = RequestMethod.GET)

	public ResponseEntity<?> createLabel(@RequestParam ("id") int id,@RequestBody Label label, HttpServletRequest request) {
		try {if(noteService.createLabel(id, label, request))
			return new ResponseEntity<String>("Label Succesfully Created",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Details are not proper",HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("pls provide the proper LabelName",HttpStatus.CONFLICT);
	}
	@RequestMapping(value="/deletelabel",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteLabel(@RequestParam("id") int id ,HttpServletRequest request)
	{
		try {
			if (noteService.deleteLabel(id,request)!=null)
				return new ResponseEntity<String>("User Succesfully deleted",HttpStatus.FOUND);
			else
				return  new ResponseEntity<String>("User not Found by given  Id",HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}
	@RequestMapping(value = "/editlabel", method = RequestMethod.PUT)
	public ResponseEntity<String> editLabel(@RequestParam("id") int id,@RequestBody Label label, HttpServletRequest request)
	{
		try {
			if (noteService.editLabel(id, label,request)!=null)
				return new ResponseEntity<String>("Note Succesfully updated",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Note not Found by given  Id",HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}
	@RequestMapping(value = "/retrievelabel", method = RequestMethod.GET)
	public ResponseEntity<?> retriveLabel(@RequestParam("id") int user_Id,HttpServletRequest request) {
		List<Label> listOfNote = noteService.retrieveLabel(user_Id,request);
		if (!listOfNote.isEmpty()) {
			return new ResponseEntity<List<Label>>(listOfNote, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("invalid user ID",HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/mergelabel&note", method = RequestMethod.POST)
	public ResponseEntity<String> mapNoteToLabel(@RequestHeader("token") String token,@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId, HttpServletRequest request)
	{
		//try {
		if (noteService.mapNoteToLabel(token,noteId,labelId, request))
			return new ResponseEntity<String>("Mapped Succesfully",HttpStatus.OK);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//	return new ResponseEntity<String>("Note not Found by given  Id",HttpStatus.CONFLICT);
		//	}
		return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/deletenote&label", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNoteLabel(@RequestHeader("token") String token,@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId, HttpServletRequest request) {
		if(noteService.removeNoteLabel(token,noteId,labelId,request))
		{
			//return new ResponseEntity<List<Label>>(listOfNote, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("invalid note/label ID",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("invalid note/label ID",HttpStatus.NOT_FOUND);
	}

}