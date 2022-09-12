package com.bridgelabz.fundoonoteservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private int errorCode;
    private String message;
    private Object token;

    public Response() {
    }
}
