package com.codurance.merlin.authentication;

import com.codurance.merlin.infrastructure.authentication.User;
import spark.Request;
import spark.Response;

public class AuthenticationController {

    public static final String CODE = "code";
    public static final String TOKEN = "token";
    private Authenticator authenticator;

    public AuthenticationController(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public Object authenticate(Request request, Response response) {
        String code = request.queryParams(CODE);

        try {
            User user = authenticator.authenticate(code);

            request.session().attribute(TOKEN, user.getUserId());
        } catch (AuthenticationException e) {
            request.session().invalidate();
        }

        response.redirect("/");

        return null;
    }
}
