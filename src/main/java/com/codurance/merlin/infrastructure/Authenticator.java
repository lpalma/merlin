package com.codurance.merlin.infrastructure;

import java.io.IOException;

public interface Authenticator {
    boolean isNotAuthenticated(String token) throws IOException;

    String getAuthenticationUrl();

    User authenticate(String code) throws IOException;
}
