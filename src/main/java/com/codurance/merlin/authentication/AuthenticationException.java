package com.codurance.merlin.authentication;

public class AuthenticationException extends Exception {
    public AuthenticationException(Exception e) {
        super(e);
    }
}
