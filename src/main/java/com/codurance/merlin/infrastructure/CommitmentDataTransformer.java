package com.codurance.merlin.infrastructure;

import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CraftspersonId;
import com.codurance.merlin.commitment.ProjectId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.time.LocalDate;

public class CommitmentDataTransformer {

    public static final String CRAFTSPERSON_ID = "craftspersonId";
    public static final String PROJECT_ID = "projectId";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    public CommitmentData fromJson(String jsonCommitment) {
        Gson gson = new Gson();

        JsonObject json = gson.fromJson(jsonCommitment, JsonObject.class);

        return new CommitmentData(
            new CraftspersonId(json.get(CRAFTSPERSON_ID).getAsString()),
            new ProjectId(json.get(PROJECT_ID).getAsString()),
            LocalDate.parse(json.get(START_DATE).getAsString()),
            LocalDate.parse(json.get(END_DATE).getAsString())
        );
    }
}
