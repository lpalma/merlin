package com.codurance.merlin;

import com.codurance.merlin.infrastructure.AuthenticationFilter;
import com.codurance.merlin.api.Routes;
import com.codurance.merlin.infrastructure.Authenticator;
import com.codurance.merlin.infrastructure.GoogleOAuthClient;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.IOException;

import static com.codurance.merlin.infrastructure.GoogleOAuthClient.buildGoogleOauthClient;

public class Main {

    public static void main(String[] args) throws IOException {
        Authenticator authenticator = buildAuthenticator();

        AuthenticationFilter filter = new AuthenticationFilter(authenticator);

        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

        Routes routes = new Routes();

        routes.init(authenticator, templateEngine, filter);
    }

    private static Authenticator buildAuthenticator() throws IOException {
        GoogleOAuthClient googleAuth = buildGoogleOauthClient();

        return new Authenticator(googleAuth);
    }
}

