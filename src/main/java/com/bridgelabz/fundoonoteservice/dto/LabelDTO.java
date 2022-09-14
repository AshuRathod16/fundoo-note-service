package com.bridgelabz.fundoonoteservice.dto;

import com.bridgelabz.fundoonoteservice.model.NoteModel;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 13-09-2022
 * Purpose : dto for the Label data
 */
@Data
public class LabelDTO {

    @NotNull(message = "The label name field should not be null")
    private String labelName;
}
