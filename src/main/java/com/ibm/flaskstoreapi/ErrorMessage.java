package com.ibm.flaskstoreapi;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorMessage{
    private String error;
    private String message;

    public ErrorMessage(String error, String message) {
        this.error = error;
        this.message = message;
    }

}