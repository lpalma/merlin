package com.codurance.merlin.api;

import com.codurance.merlin.infrastructure.AuthenticationFilter;
import com.codurance.merlin.infrastructure.User;
import com.codurance.merlin.infrastructure.Authenticator;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

import static spark.Spark.*;

public class Routes {
    public void init(Authenticator authenticator, MustacheTemplateEngine templateEngine, AuthenticationFilter filter) {
        port(8080);

        staticFileLocation("public");

        before("/*", filter);

        get("/callback", ((request, response) -> {
            String code = request.queryParams("code");
            User user = authenticator.authenticate(code);

            request.session().attribute("token", user.getUserId());

            response.redirect("/");

            return null;
        }));

        get("/", (req, res) -> new ModelAndView(new HashMap<>(), "index.mustache"), templateEngine);
    }
}
