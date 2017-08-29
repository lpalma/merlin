package com.codurance.merlin.infrastructure;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class GoogleAuthenticator implements Authenticator {

    public static final String TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=";
    public static final String NEW_LINE = "\n";
    public GoogleOAuthClient googleAuth;

    public GoogleAuthenticator(GoogleOAuthClient client) {
        this.googleAuth = client;
    }

    @Override
    public boolean isNotAuthenticated(String token) throws IOException {
        if (token == null) {
            return true;
        }

        Credential credential = googleAuth.loadCredentials(token);

        return (credential == null) || (credential.getExpiresInSeconds() <= 0);
    }

    @Override
    public String getLoginUrl() {
         return googleAuth.getNewAuthorizationUrl(googleAuth.callbackUrl());
    }

    @Override
    public User authenticate(String code) throws IOException {
        GoogleTokenResponse googleResponse = googleAuth.getTokenResponse(googleAuth.callbackUrl(), code);

        User user = getGoogleUser(googleResponse);

        if (user.isFromCodurance()) {
            googleAuth.createAndStoreCredentials(googleResponse, user);
        }

        return user;
    }

    private User getGoogleUser(GoogleTokenResponse googleResponse) throws IOException {
        String tokenInfoResponse = fetchGoogleUser(googleResponse);

        Gson gson = new Gson();

        return gson.fromJson(tokenInfoResponse, User.class);
    }

    private String fetchGoogleUser(GoogleTokenResponse googleResponse) throws IOException {
        String userInfo = TOKEN_INFO_URL + googleResponse.getAccessToken();
        URL url = new URL(userInfo);
        URLConnection conn = url.openConnection();

        return new BufferedReader(new InputStreamReader(conn.getInputStream()))
                .lines()
                .collect(Collectors.joining(NEW_LINE));
    }
}
