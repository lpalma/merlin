package com.codurance.merlin.infrastructure;

import java.io.IOException;

public class OauthConfigurationException extends Throwable {
    public OauthConfigurationException(IOException e) {
        super(e);
    }
}
