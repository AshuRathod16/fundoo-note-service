package com.bridgelabz.fundoonoteservice.controller;

import com.bridgelabz.fundoonoteservice.dto.NoteDTO;
import com.bridgelabz.fundoonoteservice.model.NoteModel;
import com.bridgelabz.fundoonoteservice.service.INoteService;
import com.bridgelabz.fundoonoteservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    INoteService noteService;


    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to fundoo notes project";
    }

    @PostMapping("/createNote")
    public ResponseEntity<Response> createNote(@Valid @RequestBody NoteDTO noteDTO, @RequestHeader String token) {
        NoteModel noteDto = noteService.createNote(noteDTO, token);
        Response response = new Response(200, "mentor inserted successfully", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("updateNote/{id}")
    public ResponseEntity<Response> updateMentor(@Valid @RequestBody NoteDTO noteDTO, @PathVariable Long noteId, @RequestHeader String token) {
        NoteModel noteModel = noteService.updateNotes(noteDTO, noteId, token);
        Response response = new Response(200, "Note updated successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/readNotes/{id}")
    public ResponseEntity<Response> getMentorById(@PathVariable Long noteId, @RequestHeader String token) {
        Optional<NoteModel> noteModel = noteService.readNotesById(noteId, token);
        Response response = new Response(200, "Note by id fetch successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/readAllNotes")
    public ResponseEntity<Response> readAllNotes(@RequestHeader String token) {
        List<NoteModel> noteModel = noteService.readAllNotes(token);
        Response response = new Response(200, "all notes fetch successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/archiveNotes/{id}")
    public ResponseEntity<Response> archiveNotes(@RequestHeader String token,@PathVariable Long noteId) {
        NoteModel noteModel = noteService.archiveNote(token, noteId);
        Response response = new Response(200, "All archive notes fetch successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/unArchiveNotes/{id}")
    public ResponseEntity<Response> unArchiveNotes(@RequestHeader String token, @PathVariable Long noteId) {
        NoteModel noteModel = noteService.unArchiveNote(token, noteId);
        Response response = new Response(200, "All unarchive notes fetch successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/restoreNotes/{id}")
    public ResponseEntity<Response> restoreNotes( @PathVariable Long noteId, @RequestHeader String token) {
        NoteModel noteModel = noteService.restoreNote(noteId, token);
        Response response = new Response(200, "Restore notes successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pinNotes/{id}")
    public ResponseEntity<Response> pinNote( @PathVariable Long noteId, @RequestHeader String token) {
        NoteModel noteModel = noteService.pinNote(noteId, token);
        Response response = new Response(200, "Pin notes got successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/unpinNotes/{id}")
    public ResponseEntity<Response> unPinNote( @PathVariable Long noteId, @RequestHeader String token) {
        NoteModel noteModel = noteService.unPinNote(noteId, token);
        Response response = new Response(200, "Unpin notes got successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/changeNoteColor/{id}")
    public ResponseEntity<Response> changeNoteColor( @PathVariable Long noteId, @RequestParam String colour , @RequestHeader String token) {
        NoteModel noteModel = noteService.changeNoteColor(noteId,colour, token);
        Response response = new Response(200, "notes color change  successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trashNote/{id}")
    public ResponseEntity<Response> trashNotes(@RequestHeader String token, @PathVariable Long noteId) {
        NoteModel noteModel = noteService.trashNote(token, noteId);
        Response response = new Response(200, "Trash notes fetch successfully", noteModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/deleteNote/{id}")
    public ResponseEntity<Response> deleteNote( @RequestHeader String token, @PathVariable Long noteId) {
            NoteModel noteModel = noteService.trashNote(token, noteId);
            Response response = new Response(200, "delete notes successfully", noteModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
