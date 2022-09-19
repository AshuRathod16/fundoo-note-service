package com.bridgelabz.fundoonoteservice.util;

import com.bridgelabz.fundoonoteservice.model.NoteModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private int errorCode;
    private String message;
    private Object token;

    public Response(int i, String successfully, NoteModel noteModel, Object isEmailIdPresent) {
    }

    public Response() {

    }
}
