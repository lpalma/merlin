package com.codurance.merlin.authentication;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

import java.io.IOException;

import static java.util.Collections.singleton;

public class MerlinOAuthClient {

    private static final String EMAIL = "email";
    private AuthorizationCodeFlow authorizationFlow;
    private OAuthConfig oAuthConfig;

    private MerlinOAuthClient(AuthorizationCodeFlow authorizationFlow, OAuthConfig oAuthConfig) {
        this.authorizationFlow = authorizationFlow;
        this.oAuthConfig = oAuthConfig;
    }

    public static MerlinOAuthClient buildOauthClient() throws OauthConfigurationException {
        OAuthConfig oAuthConfig = new OAuthConfig().load();

        GoogleAuthorizationCodeFlow googleAuth = initialiseCodeFlow(oAuthConfig.getCliendId(), oAuthConfig.getClientSecret());

        return new MerlinOAuthClient(googleAuth, oAuthConfig);
    }

    public TokenResponse getTokenResponse(String code) throws AuthenticationException {
        try {
            return authorizationFlow.newTokenRequest(code)
                    .setRedirectUri(callbackUrl())
                    .execute();
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }
    }

    public String getNewAuthorizationUrl() {
        return authorizationFlow.newAuthorizationUrl()
                .setRedirectUri(callbackUrl())
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

    private String callbackUrl() {
        return oAuthConfig.getCallbackUrl();
    }

    private static GoogleAuthorizationCodeFlow initialiseCodeFlow(String cliendId, String clientSecret) throws OauthConfigurationException {
        GoogleAuthorizationCodeFlow googleAuth;

        try {
            googleAuth = new GoogleAuthorizationCodeFlow
                    .Builder(new ApacheHttpTransport(), new JacksonFactory(), cliendId, clientSecret, singleton(EMAIL))
                    .setDataStoreFactory(MemoryDataStoreFactory.getDefaultInstance())
                    .build();
        } catch (IOException e) {
            throw new OauthConfigurationException(e);
        }

        return googleAuth;
    }
}
