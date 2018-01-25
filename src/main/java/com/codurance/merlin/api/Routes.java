package com.codurance.merlin.api;

import com.codurance.merlin.authentication.AuthenticationController;
import com.codurance.merlin.authentication.Authenticator;
import com.codurance.merlin.infrastructure.AuthorisationFilter;
import com.codurance.merlin.infrastructure.CommitmentDataTransformer;
import com.codurance.merlin.infrastructure.JsonTransformer;
import com.codurance.merlin.infrastructure.persistence.MerlinRepositoryContext;
import com.codurance.merlin.service.CommitmentService;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Routes {

    private AuthenticationController authenticationController;
    private Authenticator authenticator;
    private CommitmentsAPI commitmentsAPI;

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
        CommitmentService commitmentService = new CommitmentService(MerlinRepositoryContext.getCommitmentRepository());

        authenticationController = new AuthenticationController(this.authenticator);
        commitmentsAPI = new CommitmentsAPI(commitmentService, new CommitmentDataTransformer());
    }

    private void initialiseMainRoutes(TemplateEngine templateEngine) {
        get("/", (req, res) -> render(new HashMap<>(), "index.mustache", templateEngine));
    }

    private void initialiseAuthenticationRoutes() {
        get("/callback", authenticationController::authenticate);
    }

    private void initialiseApiRoutes() {
        path("/api", () -> {
            get("/commitments", "application/json", commitmentsAPI::getAll, new JsonTransformer());
            put("/commitments", "application/json", commitmentsAPI::add, new JsonTransformer());
            delete("/commitments/:id", "application/json", commitmentsAPI::delete);
        });
    }

    private static String render(Map<String, Object> model, String view, TemplateEngine templateEngine) {
        return templateEngine.render(
                new ModelAndView(model, view)
        );
    }
}
