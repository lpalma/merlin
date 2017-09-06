package com.codurance.merlin.infrastructure;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

import java.io.IOException;

import static java.util.Collections.singleton;

public class MerlinOAuthClient {

    public static final String EMAIL = "email";
    private AuthorizationCodeFlow authorizationFlow;
    private OAuthConfig oAuthConfig;

    public MerlinOAuthClient(AuthorizationCodeFlow authorizationFlow, OAuthConfig oAuthConfig) {
        this.authorizationFlow = authorizationFlow;
        this.oAuthConfig = oAuthConfig;
    }

    public static MerlinOAuthClient buildOauthClient() throws AuthenticationException {
        OAuthConfig oAuthConfig = new OAuthConfig().build();

        String cliendId = oAuthConfig.getCliendId();
        String clientSecret = oAuthConfig.getClientSecret();

        HttpTransport transport = new ApacheHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleAuthorizationCodeFlow googleAuth;

        try {
            googleAuth = new GoogleAuthorizationCodeFlow
                    .Builder(transport, jsonFactory, cliendId, clientSecret, singleton(EMAIL))
                    .setDataStoreFactory(MemoryDataStoreFactory.getDefaultInstance())
                    .build();
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }

        return new MerlinOAuthClient(googleAuth, oAuthConfig);
    }

    public TokenResponse getTokenResponse(String callbackUrl, String code) throws AuthenticationException {
        try {
            return authorizationFlow.newTokenRequest(code)
                    .setRedirectUri(callbackUrl)
                    .execute();
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }
    }

    public String getNewAuthorizationUrl(String callbackUrl) {
        return authorizationFlow.newAuthorizationUrl()
                .setRedirectUri(callbackUrl)
                .setScopes(singleton(EMAIL))
                .build();
    }

    public Credential loadCredentials(String userId) throws AuthenticationException {
        try {
            return authorizationFlow.loadCredential(userId);
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }
    }

    public void createAndStoreCredentials(TokenResponse tokenResponse, User user) throws AuthenticationException {
        try {
            authorizationFlow.createAndStoreCredential(tokenResponse, user.getUserId());
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }
    }

    public String callbackUrl() {
        return oAuthConfig.getCallbackUrl();
    }
}
