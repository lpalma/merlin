package com.codurance.merlin;

import com.codurance.merlin.api.Routes;
import com.codurance.merlin.infrastructure.AuthorisationFilter;
import com.codurance.merlin.infrastructure.MerlinAuthenticator;
import com.codurance.merlin.infrastructure.MerlinOAuthClient;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.IOException;

import static com.codurance.merlin.infrastructure.MerlinOAuthClient.buildOauthClient;

public class Main {

    public static void main(String[] args) throws IOException {
        MerlinAuthenticator authenticator = buildAuthenticator();

        AuthorisationFilter filter = new AuthorisationFilter(authenticator);

        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

        Routes routes = new Routes();

        routes.init(authenticator, templateEngine, filter);
    }

    private static MerlinAuthenticator buildAuthenticator() throws IOException {
        MerlinOAuthClient oauthClient = buildOauthClient();

        return new MerlinAuthenticator(oauthClient);
    }
}

