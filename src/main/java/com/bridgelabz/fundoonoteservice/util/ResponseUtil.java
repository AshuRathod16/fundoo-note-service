package com.bridgelabz.fundoonoteservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUtil {
    private int errorCode;
    private String message;
    private Object token;

}
