package com.codurance.merlin.api;

import com.codurance.merlin.infrastructure.AuthenticationFilter;
import com.codurance.merlin.infrastructure.Authenticator;
import com.codurance.merlin.infrastructure.User;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Routes {

    public static final String PROJECT_COMMITMENTS = "PROJECT_COMMITMENTS";
    public static final String PROJECT_COMMITMENTS_FILE = "project_commitments.json";

    public void init(Authenticator authenticator, TemplateEngine templateEngine, AuthenticationFilter filter) {
        port(8080);

        staticFileLocation("public");

        before("/*", filter);

        initialiseMainRoutes(templateEngine);
        initialiseAuthenticationRoutes(authenticator);
        initialiseApiRoutes();
    }

    private void initialiseMainRoutes(TemplateEngine templateEngine) {
        get("/", (req, res) -> render(new HashMap<>(), "index.mustache", templateEngine));
    }

    private void initialiseAuthenticationRoutes(Authenticator authenticator) {
        get("/callback", ((request, response) -> {
            String code = request.queryParams("code");
            User user = authenticator.authenticate(code);

            request.session().attribute("token", user.getUserId());

            response.redirect("/");

            return null;
        }));
    }

    private void initialiseApiRoutes() {
        path("/api", () -> {
            get("/commitments", "application/json", (req, res) -> loadCommitments());
        });
    }

    private String loadCommitments() {
        String commitmentsJson;

        try {
            commitmentsJson = String.join("", Files.readAllLines(Paths.get(PROJECT_COMMITMENTS_FILE)));
        } catch (IOException e) {
            commitmentsJson = System.getenv(PROJECT_COMMITMENTS);
        }

        return commitmentsJson;
    }

    private static String render(Map<String, Object> model, String view, TemplateEngine templateEngine) {
        return templateEngine.render(
                new ModelAndView(model, view)
        );
    }
}
