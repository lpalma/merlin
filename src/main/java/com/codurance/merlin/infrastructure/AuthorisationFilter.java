package com.codurance.merlin.infrastructure;

import spark.Filter;
import spark.Request;
import spark.Response;

public class AuthorisationFilter implements Filter {

    private Authenticator authenticator;

    public AuthorisationFilter(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        if (this.isNotCallbackUrl(request) && this.isNotAuthenticated(request)) {
            request.session().invalidate();
            String url = authenticator.getAuthenticationUrl();
            response.redirect(url);
        }
    }

    private boolean isNotAuthenticated(Request request) throws AuthenticationException {

        return authenticator.isNotAuthenticated(request.session().attribute("token"));
    }

    private boolean isNotCallbackUrl(Request request) {

        return !request.url().contains("/callback");
    }
}
