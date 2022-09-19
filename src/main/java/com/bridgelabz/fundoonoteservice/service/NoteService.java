package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.NoteDTO;
import com.bridgelabz.fundoonoteservice.exception.NoteException;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.model.NoteModel;
import com.bridgelabz.fundoonoteservice.repository.LabelRepository;
import com.bridgelabz.fundoonoteservice.repository.NoteRepository;
import com.bridgelabz.fundoonoteservice.util.Response;
import com.bridgelabz.fundoonoteservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 13-09-2022
 * Purpose: Creating method to send Email
 */

@Service
public class NoteService implements INoteService {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    MailService mailService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LabelRepository labelRepository;

    /**
     * @author Ashwini Rathod
     * @param token, noteDTO
     * Purpose: Creating method to create notes
     */

    @Override
    public NoteModel createNote(NoteDTO nodeDto, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            NoteModel model = new NoteModel(nodeDto);
            model.setUserId(userId);
            model.setArchive(false);
            model.setPin(false);
            model.setTrash(false);
            model.setRegisterDate(LocalDateTime.now());
            noteRepository.save(model);
            String body = "Note added successfully with noteId" + model.getNoteId();
            String subject = "Note created successfully";
            return model;
        }
        throw new NoteException(400, "Note not created ");
    }

    /**
     * @author Ashwini Rathod
     * @param token,noteDTO,noteId
     * Purpose: Creating method to update notes
     */

    @Override
    public NoteModel updateNotes(NoteDTO noteDTO, Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
                if (isNotePresent.isPresent()) {
                    isNotePresent.get().setTitle(noteDTO.getTitle());
                    isNotePresent.get().setDescription(noteDTO.getDescription());
                    isNotePresent.get().setUserId(noteDTO.getUserId());
                    isNotePresent.get().setLabelId(noteDTO.getLabelId());
                    isNotePresent.get().setColour(noteDTO.getColour());
                    noteRepository.save(isNotePresent.get());
                    String body = "Note updated successfully with note id" + isNotePresent.get().getNoteId();
                    String subject = "Note updated successfully";
                    mailService.send(isNotePresent.get().getEmailId(), subject, body);
                    return isNotePresent.get();
                }
            }
            throw new NoteException(400, "Note not found");
        }
        throw new NoteException(400, "Token is invalid");

    }

    /**
     * @author Ashwini Rathod
     * @param token
     * purpose: Creating Method to read all notes by id
     */

    @Override
    public List<NoteModel> readAllNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                List<NoteModel> readAllNotes = noteRepository.findAll();
                if (readAllNotes.size() > 0) {
                    return readAllNotes;
                }
            }
            throw new NoteException(400, "Notes with this id not found");
        }
        throw new NoteException(400, "Token is invalid");
    }

    /**
     * @author Ashwini Rathod
     * @param token,noteId
     * purpose: Creating method to read notes by id
     */

    @Override
    public Optional<NoteModel> readNotesById(Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
                if (isNotePresent.isPresent()) {
                    return isNotePresent;
                }
            }
            throw new NoteException(400, "Note not found");
        }
        throw new NoteException(400, "Token is invalid");
    }

    /**
     * @author Ashwini Rathod
     * @param token,noteId
     * purpose: Creating method to archive notes
     */

    @Override
    public NoteModel archiveNote(String token, Long noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
                if (isNotePresent.isPresent()) {
                    isNotePresent.get().setArchive(true);
                    noteRepository.save(isNotePresent.get());
                    return isNotePresent.get();
                }
            }
            throw new NoteException(400, "Note with this id is not found");
        }
        throw new NoteException(400, "Invalid token");
    }


    /**
     * @author Ashwini Rathod
     * @param token
     * purpose: Creating method get all archive notes by user id
     */
    @Override
    public List<NoteModel> getAllArchiveNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                List<NoteModel> pinList = noteRepository.findByIsArchive();
                if (pinList.size() > 0) {
                    return pinList;
                }
            }
            throw new NoteException(400, "Notes with this id not found");
        }
        throw new NoteException(400, "Token is invalid");
    }


    /**
     * @author  Ashwini Rathod
     * @param   token,noteId
     * purpose: Creating method to unarchive notes
     */
    @Override
    public NoteModel unArchiveNote(String token, Long noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
                if (isNotePresent.isPresent()) {
                    isNotePresent.get().setArchive(false);
                    noteRepository.save(isNotePresent.get());
                    return isNotePresent.get();
                }
            }
            throw new NoteException(400, "Note with this id is not found");
        }
        throw new NoteException(400, "Invalid token");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,noteId
     * purpose: Creating method to restoring notes
     */
    @Override
    public NoteModel restoreNote(long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
                if (isIdPresent.isPresent()) {
                    isIdPresent.get().setTrash(true);
                    isIdPresent.get().setArchive(false);
                    noteRepository.save(isIdPresent.get());
                    return isIdPresent.get();

                }
            }
            throw new NoteException(400, "Note with this id is not found");
        }
        throw new NoteException(400, "Token is wrong");

    }

    /**
     * @author Ashwini Rathod
     * @param token,noteId
     * purpose: Creating method to pin notes
     */

    @Override
    public NoteModel pinNote(Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
                if (isIdPresent.isPresent()) {
                    isIdPresent.get().setPin(true);
                    noteRepository.save(isIdPresent.get());
                    return isIdPresent.get();
                }
            }
            throw new NoteException(400, "Note with this id is not found");

        }
        throw new NoteException(400, "Token is wrong");

    }

    /**
     * @author Ashwini Rathod
     * @param token
     * purpose: Creating method to get all pin notes
     */
    @Override
    public List<NoteModel> getAllPinNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                List<NoteModel> pinList = noteRepository.findByIsPin();
                if (pinList.size() > 0) {
                    return pinList;
                }
            }
            throw new NoteException(400, "Notes with this id not found");
        }
        throw new NoteException(400, "Token is invalid");
    }


    /**
     * @author  Ashwini Rathod
     * @param   token,noteId
     * purpose: Creating method to unpin notes
     */
    @Override
    public NoteModel unPinNote(Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
            if (isIdPresent.isPresent()) {
                isIdPresent.get().setPin(false);
                noteRepository.save(isIdPresent.get());
                return isIdPresent.get();
            } else {
                throw new NoteException(400, "Note with this id is not found");
            }
        } else {
            throw new NoteException(400, "Token is wrong");
        }
    }


    /**
     * @author  Ashwini Rathod
     * @param   token,noteId,colour
     * purpose: Creating method to changing colour of notes
     */

    @Override
    public NoteModel changeNoteColor(Long noteId, String colour, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
                if (isIdPresent.isPresent()) {
                    isIdPresent.get().setColour(colour);
                    noteRepository.save(isIdPresent.get());
                    return isIdPresent.get();
                }
            }
            throw new NoteException(400, "Note with this id is not found");

        } else {
            throw new NoteException(400, "Token is wrong");
        }
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,noteId
     * purpose: Creating method to trash notes
     */
    @Override
    public NoteModel trashNote(String token, Long noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
                if (isNotePresent.isPresent()) {
                    isNotePresent.get().setTrash(true);
                    noteRepository.save(isNotePresent.get());
                    return isNotePresent.get();
                }
            }
            throw new NoteException(400, "Note with this id is not found");
        }
        throw new NoteException(400, "Invalid token");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token
     * purpose: Creating method to get all trash notes
     */
    @Override
    public List<NoteModel> getAllTrashNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                List<NoteModel> trashList = noteRepository.findByTrash();
                if (trashList.size() > 0) {
                    return trashList;
                }
            }
            throw new NoteException(400, "Notes with this id not found");
        }
        throw new NoteException(400, "Token is invalid");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,noteId
     * purpose: Creating method to delete notes
     */
    @Override
    public Response deleteNote(String token, Long noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
                if (isIdPresent.isPresent()) {
                    return new Response(200, "Successfully", isIdPresent.get());
                }
            }
            throw new NoteException(400, "User not found");
        }
        throw new NoteException(400, "Invalid token");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,noteId,reminder
     * purpose: Creating method to set reminder of notes
     */

    @Override
    public Response setReminder(Long noteId, String reminder, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
                if (isNotePresent.isPresent()) {
                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss");
                    LocalDate reminde = LocalDate.parse(reminder, formatter);
                    if (reminde.isBefore(today))
                        throw new NoteException(400, "Date is before original time");
                    isNotePresent.get().setReminderTime(reminder);
                    noteRepository.save(isNotePresent.get());
                    return isNotePresent.get();
                }
            }
            throw new NoteException(400, "Note with this id not found");
        }
        throw new NoteException(400, "Invalid Token");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,noteId,labelId
     * purpose: Creating method to add label to notes
     */

    @Override
    public Response addLabels(List<Long> labelId, Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<LabelModel> isLabelPresent = new ArrayList<>();
            labelId.stream().forEach(label -> {
                Optional<LabelModel> isLabel = labelRepository.findById(label);
                if (isLabel.isPresent()) {
                    isLabelPresent.add(isLabel.get());
                }
            });
            Optional<NoteModel> note = noteRepository.findById(noteId);
            if (isLabelPresent.size() > 0) {
                note.get().setLabelList(isLabelPresent);
                noteRepository.save(note.get());
                return note.get();
            }
            throw new NoteException(400, "Note not found");
        }
        throw new NoteException(400, "Invalid token");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,noteId,emailId,collaborator,collaboratorId
     * purpose: Creating method to unpin notes
     */
    @Override
    public Response addCollaborator(String token, String emailId, Long noteId, String collaborator) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteModel> isUserIdPresent = noteRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Object isEmailPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + emailId, Object.class);
                if (!isEmailPresent.equals(null)) {
                    Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
                    if (isNotePresent.isPresent()) {
                        List<String> collabList = new ArrayList<>();
                        Object isEmailIdPresent = restTemplate.getForObject("http://FUNDOO-USER:8082/user/validate/" + collaborator, Object.class);
                        if (!isEmailIdPresent.equals(null)) {

                            collabList.add(collaborator);
                        } else {
                            throw new NoteException(400, "Email not found");
                        }
                        isNotePresent.get().setCollaborator(collabList);
                        noteRepository.save(isNotePresent.get());
                        List<String> noteList = new ArrayList<>();
                        noteList.add(isNotePresent.get().getEmailId());

                        NoteModel noteModel = new NoteModel();
//                        noteModel.setUserId(collaboratorId);
                        noteModel.setTitle(isNotePresent.get().getTitle());
                        noteModel.setDescription(isNotePresent.get().getDescription());
                        noteModel.setCollaborator(noteList);
                        noteRepository.save(noteModel);
                        return new Response(200, "Successfully", isNotePresent.get(), isEmailIdPresent);
                    } else {
                        throw new NoteException(400, "User not found");
                    }
                }
            }
            throw new NoteException(400, "Invalid Email");
        }
        return null;
    }
}


