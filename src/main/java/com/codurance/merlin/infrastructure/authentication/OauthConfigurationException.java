package com.codurance.merlin.infrastructure.authentication;

import java.io.IOException;

public class OauthConfigurationException extends Throwable {
    public OauthConfigurationException(IOException e) {
        super(e);
    }
}
