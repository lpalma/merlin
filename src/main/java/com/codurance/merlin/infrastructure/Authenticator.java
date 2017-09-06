package com.codurance.merlin.infrastructure;

public interface Authenticator {
    boolean isNotAuthenticated(String token) throws AuthenticationException;

    String getAuthenticationUrl();

    User authenticate(String code) throws AuthenticationException;
}
