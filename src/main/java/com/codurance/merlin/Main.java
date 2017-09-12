package com.codurance.merlin;

import com.codurance.merlin.api.Routes;
import com.codurance.merlin.authentication.MerlinAuthenticator;
import com.codurance.merlin.authentication.MerlinOAuthClient;
import com.codurance.merlin.authentication.OauthConfigurationException;
import com.codurance.merlin.infrastructure.*;
import spark.template.mustache.MustacheTemplateEngine;

import static com.codurance.merlin.authentication.MerlinOAuthClient.buildOauthClient;

public class Main {

    public static void main(String[] args) throws OauthConfigurationException {
        MerlinAuthenticator authenticator = buildAuthenticator();

        AuthorisationFilter filter = new AuthorisationFilter(authenticator);

        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

        Routes routes = new Routes();

        routes.init(authenticator, templateEngine, filter);
    }

    private static MerlinAuthenticator buildAuthenticator() throws OauthConfigurationException {
        MerlinOAuthClient oauthClient = buildOauthClient();

        return new MerlinAuthenticator(oauthClient);
    }
}

