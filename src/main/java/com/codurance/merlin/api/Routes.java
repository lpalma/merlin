package com.codurance.merlin.api;

import com.codurance.merlin.authentication.AuthenticationController;
import com.codurance.merlin.authentication.Authenticator;
import com.codurance.merlin.commitment.CommitmentsController;
import com.codurance.merlin.infrastructure.AuthorisationFilter;
import com.codurance.merlin.infrastructure.JsonTransformer;
import com.codurance.merlin.infrastructure.persistence.MerlinRepositoryContext;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Routes {

    private AuthenticationController authenticationController;
    private Authenticator authenticator;
    private CommitmentsController commitmentsController;

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
        commitmentsController = new CommitmentsController(MerlinRepositoryContext.getCommitmentRepository());
    }

    private void initialiseMainRoutes(TemplateEngine templateEngine) {
        get("/", (req, res) -> render(new HashMap<>(), "index.mustache", templateEngine));
    }

    private void initialiseAuthenticationRoutes() {
        get("/callback", authenticationController::authenticate);
    }

    private void initialiseApiRoutes() {
        path("/api", () -> {
            get("/commitments", "application/json", commitmentsController::getAll, new JsonTransformer());
            put("/commitments", "application/json", commitmentsController::add, new JsonTransformer());
        });
    }

    private static String render(Map<String, Object> model, String view, TemplateEngine templateEngine) {
        return templateEngine.render(
                new ModelAndView(model, view)
        );
    }
}
