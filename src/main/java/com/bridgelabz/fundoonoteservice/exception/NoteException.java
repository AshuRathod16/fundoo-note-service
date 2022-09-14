package com.bridgelabz.fundoonoteservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Purpose : NoteException is used to handle the exceptions
 * Version : 1.0
 * @since  : 13-09-2022
 * @author : Ashwini Rathod
 * */

@ResponseStatus
public class NoteException extends RuntimeException{
    private int statusCode;
    private String statusMessage;

    public NoteException(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
