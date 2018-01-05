package com.codurance.merlin;

import com.codurance.merlin.api.Routes;
import com.codurance.merlin.infrastructure.authentication.MerlinAuthenticator;
import com.codurance.merlin.infrastructure.authentication.MerlinOAuthClient;
import com.codurance.merlin.infrastructure.authentication.OauthConfigurationException;
import com.codurance.merlin.infrastructure.AuthorisationFilter;
import com.codurance.merlin.infrastructure.persistence.DatabaseConfig;
import spark.template.mustache.MustacheTemplateEngine;

import static com.codurance.merlin.infrastructure.authentication.MerlinOAuthClient.buildOauthClient;

public class Merlin {

    public static void main(String[] args) throws OauthConfigurationException {
        DatabaseConfig.migrate();

        initialiseRoutes();
    }

    private static void initialiseRoutes() throws OauthConfigurationException {
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
