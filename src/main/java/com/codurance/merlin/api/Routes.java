package com.codurance.merlin.api;

import com.codurance.merlin.infrastructure.AuthenticationFilter;
import com.codurance.merlin.infrastructure.Authenticator;
import com.codurance.merlin.infrastructure.User;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Routes {

    public static final String COMMITMENTS = "PROJECT_COMMITMENTS";
    public static final String COMMITMENTS_FILE = "commitments.json";

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

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return render(model, "index.mustache");
        });

        path("/api", () -> {
            get("/commitments", "application/json", (req, res) -> loadCommitments());
        });
    }

    private String loadCommitments() {
        String commitmentsJson;

        try {
            commitmentsJson = String.join("", Files.readAllLines(Paths.get(COMMITMENTS_FILE)));
        } catch (IOException e) {
            commitmentsJson = System.getenv(COMMITMENTS);
        }

        return commitmentsJson;
    }

    private static String render(Map<String, Object> model, String view) {
        return new MustacheTemplateEngine().render(
                new ModelAndView(model, view)
        );
    }

}
