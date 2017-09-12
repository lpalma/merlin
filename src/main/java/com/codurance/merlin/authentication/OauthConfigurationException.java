package com.codurance.merlin.authentication;

import java.io.IOException;

public class OauthConfigurationException extends Throwable {
    public OauthConfigurationException(IOException e) {
        super(e);
    }
}
