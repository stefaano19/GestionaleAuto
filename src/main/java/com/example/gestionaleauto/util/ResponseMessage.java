package com.example.gestionaleauto.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }
    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>("{\"messag\":\""+message+"\"}", httpStatus);
    }

    public String getMessage() {
        return message;
    }


}