package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.NoteDTO;
import com.bridgelabz.fundoonoteservice.model.NoteModel;
import com.bridgelabz.fundoonoteservice.util.Response;
import com.bridgelabz.fundoonoteservice.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

public interface INoteService {

    public NoteModel createNote(NoteDTO nodeDto, String token);

    public NoteModel updateNotes(NoteDTO noteDTO, Long noteId, String token);

    public List<NoteModel> readAllNotes(String token);

    public Optional<NoteModel> readNotesById(Long noteId, String token);

    public NoteModel archiveNote(String token, Long noteId);

    public NoteModel unArchiveNote(String token, Long noteId);

    public NoteModel restoreNote(long noteId, String token);

    public NoteModel pinNote(Long noteId, String token);

    public NoteModel unPinNote(Long noteId, String token);

    public NoteModel changeNoteColor(Long noteId, String colour, String token);

    public NoteModel trashNote(String token, Long noteId);

    public Response deleteNote(String token, Long noteId);


}
