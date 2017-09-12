package com.codurance.merlin.authentication;

public interface Authenticator {
    boolean isNotAuthenticated(String token) throws AuthenticationException;

    String getAuthenticationUrl();

    User authenticate(String code) throws AuthenticationException;
}
