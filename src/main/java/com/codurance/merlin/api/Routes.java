package com.codurance.merlin.api;

import com.codurance.merlin.controller.AuthenticationController;
import com.codurance.merlin.infrastructure.AuthorisationFilter;
import com.codurance.merlin.infrastructure.Authenticator;
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
    private AuthenticationController authenticationController;
    private Authenticator authenticator;

    public void init(Authenticator authenticator, TemplateEngine templateEngine, AuthorisationFilter authorisationFilter) {
        this.authenticator = authenticator;

        port(8080);
        staticFileLocation("public");

        before("/*", authorisationFilter);

        initialiseControllers();
        initialiseMainRoutes(templateEngine);
        initialiseAuthenticationRoutes();
        initialiseApiRoutes();
    }

    private void initialiseControllers() {
        authenticationController = new AuthenticationController(this.authenticator);
    }

    private void initialiseMainRoutes(TemplateEngine templateEngine) {
        get("/", (req, res) -> render(new HashMap<>(), "index.mustache", templateEngine));
    }

    private void initialiseAuthenticationRoutes() {
        get("/callback", authenticationController::authenticate);
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
