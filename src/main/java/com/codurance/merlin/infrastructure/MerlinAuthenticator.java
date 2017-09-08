package com.codurance.merlin.infrastructure;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class MerlinAuthenticator implements Authenticator {

    public static final String TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=";
    public static final String NEW_LINE = "\n";
    public MerlinOAuthClient oAuthClient;

    public MerlinAuthenticator(MerlinOAuthClient client) {
        this.oAuthClient = client;
    }

    @Override
    public boolean isNotAuthenticated(String token) throws IOException {
        if (token == null) {
            return true;
        }

        Credential credential = oAuthClient.loadCredentials(token);

        return (credential == null) || (credential.getExpiresInSeconds() <= 0);
    }

    @Override
    public String getLoginUrl() {
         return oAuthClient.getNewAuthorizationUrl(oAuthClient.callbackUrl());
    }

    @Override
    public User authenticate(String code) throws IOException {
        TokenResponse tokenResponse = oAuthClient.getTokenResponse(oAuthClient.callbackUrl(), code);

        User user = getUserDetails(tokenResponse);

        if (user.isFromCodurance()) {
            oAuthClient.createAndStoreCredentials(tokenResponse, user);
        }

        return user;
    }

    private User getUserDetails(TokenResponse tokenResponse) throws IOException {
        String tokenInfoResponse = fetchUserDetails(tokenResponse);

        Gson gson = new Gson();

        return gson.fromJson(tokenInfoResponse, User.class);
    }

    private String fetchUserDetails(TokenResponse tokenResponse) throws IOException {
        String userInfo = TOKEN_INFO_URL + tokenResponse.getAccessToken();
        URL url = new URL(userInfo);
        URLConnection conn = url.openConnection();

        return new BufferedReader(new InputStreamReader(conn.getInputStream()))
                .lines()
                .collect(Collectors.joining(NEW_LINE));
    }
}
