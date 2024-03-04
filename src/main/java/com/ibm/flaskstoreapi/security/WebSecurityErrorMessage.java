package com.ibm.flaskstoreapi.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebSecurityErrorMessage {
    private String error;
    private String message;

    public WebSecurityErrorMessage(String error, String message) {
        this.error = error;
        this.message = message;
    }

}