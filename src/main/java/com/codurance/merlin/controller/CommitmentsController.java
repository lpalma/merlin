package com.codurance.merlin.controller;

import spark.Request;
import spark.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommitmentsController {
    public static final String PROJECT_COMMITMENTS = "PROJECT_COMMITMENTS";
    public static final String PROJECT_COMMITMENTS_FILE = "project_commitments.json";

    public String getAll(Request request, Response response) {
        String commitmentsJson;

        try {
            commitmentsJson = String.join("", Files.readAllLines(Paths.get(PROJECT_COMMITMENTS_FILE)));
        } catch (IOException e) {
            commitmentsJson = System.getenv(PROJECT_COMMITMENTS);
        }

        return commitmentsJson;
    }
}
