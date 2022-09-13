package com.bridgelabz.fundoonoteservice.model;

import com.bridgelabz.fundoonoteservice.dto.NoteDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "NoteService")
public class NoteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long noteId;
    private String title;
    private String description;
    private long userId;
    private boolean trash;
    private boolean isArchive;
    private boolean pin;
    private String labelId;
    private String emailId;
    private String colour;
    private LocalDateTime reminderTime;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<LabelModel> labelList;


    public NoteModel(NoteDTO noteDTO){
        this.title = noteDTO.getTitle();
        this.description = noteDTO.getDescription();
        this.userId = noteDTO.getUserId();
        this.emailId = noteDTO.getEmailId();
        this.colour = noteDTO.getColour();
        this.labelId = noteDTO.getLabelId();

    }

    public NoteModel() {
    }
}
