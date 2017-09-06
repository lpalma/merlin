package com.codurance.merlin.infrastructure;

public class AuthenticationException extends Exception {
    public AuthenticationException(Exception e) {
        super(e);
    }
}
