package com.codurance.merlin.authentication;

import com.codurance.merlin.infrastructure.authentication.User;

public interface Authenticator {
    boolean isNotAuthenticated(String token) throws AuthenticationException;

    String getAuthenticationUrl();

    User authenticate(String code) throws AuthenticationException;
}
