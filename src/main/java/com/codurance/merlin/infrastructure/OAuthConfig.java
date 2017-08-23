package com.codurance.merlin.infrastructure;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class OAuthConfig {
    public static final String FILE_PATH = "config.properties";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CLIENT_SECRET = "CLIENT_SECRET";
    public static final String CALLBACK_URL = "CALLBACK_URL";
    private String cliendId;
    private String clientSecret;
    private String callbackUrl;

    public String getCliendId() {
        return cliendId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public OAuthConfig build() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(FILE_PATH));

        cliendId = properties.getProperty(CLIENT_ID);
        clientSecret = properties.getProperty(CLIENT_SECRET);
        callbackUrl = properties.getProperty(CALLBACK_URL);

        return this;
    }
}
