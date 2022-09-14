package com.bridgelabz.fundoonoteservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Purpose : CustomValidationExceptionHandler is used to handle the exceptions
 * Version : 1.0
 * @since  : 13-09-2022
 * @author : Ashwini Rathod
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomValidationException {
    private int errorCode;
    private String message;
}
