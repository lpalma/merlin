package com.codurance.merlin.api;

import com.codurance.merlin.Commitment;
import com.codurance.merlin.infrastructure.AuthenticationFilter;
import com.codurance.merlin.infrastructure.Authenticator;
import com.codurance.merlin.infrastructure.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

import static spark.Spark.*;

public class Routes {

    public static final String PROJECT_COMMITMENTS = "PROJECT_COMMITMENTS";
    public static final String PROJECT_COMMITMENTS_FILE = "project_commitments.json";
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

        get("/", (req, res) -> new ModelAndView(loadProjectCommitments(), "index.mustache"), templateEngine);

        get("/json", "application/json", (req, res) -> loadCommitments());
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

    private Map<String, Collection<Commitment>> loadProjectCommitments(){
        Gson gson = new Gson();

        Type commitmentsMap = new TypeToken<Map<String, Collection<Commitment>>>(){}.getType();

        String commitmentsJson;

        try {
            commitmentsJson = String.join("", Files.readAllLines(Paths.get(PROJECT_COMMITMENTS_FILE)));
        } catch (IOException e) {
            commitmentsJson = System.getenv(PROJECT_COMMITMENTS);
        }

        return gson.fromJson(commitmentsJson, commitmentsMap);
    }
}
