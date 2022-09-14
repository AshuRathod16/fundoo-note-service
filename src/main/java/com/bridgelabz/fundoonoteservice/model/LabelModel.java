package com.bridgelabz.fundoonoteservice.model;


import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 13-09-2022
 * Purpose : Model for the Label data Registration
 */

@Data
@Entity
@Table(name = "Label")
public class LabelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long labelId;
    @Column(name = "labelName")
    private String labelName;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "noteId")
    private Long noteId;

    @Column(name = "registeredDate")
    private LocalDateTime registerDate;

    @Column(name = "UpdatedDate")
    private LocalDateTime updateDate;

    //    @JsonIgnore();
//    @ManyToMany(mappedBy = "labelList")
//    private List<NoteModel> notes;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<NoteModel> labelList;


    public LabelModel(LabelDTO labelDTO) {
        this.labelName = labelDTO.getLabelName();
    }

    public LabelModel() {
    }
}
