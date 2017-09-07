package com.codurance.merlin.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommitmentRepository {

    public static final String PROJECT_COMMITMENTS = "PROJECT_COMMITMENTS";
    public static final String PROJECT_COMMITMENTS_FILE = "project_commitments.json";

    public String all() {

        String commitmentsJson;

        try {
            commitmentsJson = String.join("", Files.readAllLines(Paths.get(PROJECT_COMMITMENTS_FILE)));
        } catch (IOException e) {
            commitmentsJson = System.getenv(PROJECT_COMMITMENTS);
        }

        return commitmentsJson;
    }
}
