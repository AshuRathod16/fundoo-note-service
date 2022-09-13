package com.bridgelabz.fundoonoteservice.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class NoteDTO {

    @NotNull(message = "Title should not be null")
    private String title;

    @NotNull(message = "Description should not be null")
    private String description;

    @NotNull(message = "userId should not be null")
    private long userId;

    @NotNull(message = "Email id should not be null")
    @Pattern(regexp = "[a-z][A-Z a-z 0-9]+[@][a-z]+[.][a-z]{2,}", message = "Invalid email id")
    private String emailId;

    private String labelId;

    private String colour;

}
