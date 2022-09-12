package com.bridgelabz.fundoonoteservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NoteException extends RuntimeException{
    private int statusCode;
    private String statusMessage;

    public NoteException(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
