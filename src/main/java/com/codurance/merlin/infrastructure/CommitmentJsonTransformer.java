package com.codurance.merlin.infrastructure;

import com.codurance.merlin.commitment.Commitment;
import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CraftspersonId;
import com.codurance.merlin.commitment.ProjectId;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommitmentJsonTransformer {

    private static final String CRAFTSPERSON_ID = "craftspersonId";
    private static final String PROJECT_ID = "projectId";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String COMMITMENT_ID = "commitmentId";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

    public CommitmentData fromJson(String jsonCommitment) {
        Gson gson = new Gson();

        JsonObject json = gson.fromJson(jsonCommitment, JsonObject.class);

        return new CommitmentData(
            new CraftspersonId(json.get(CRAFTSPERSON_ID).getAsString()),
            new ProjectId(json.get(PROJECT_ID).getAsString()),
            LocalDate.parse(json.get(START_DATE).getAsString(), dateTimeFormatter),
            LocalDate.parse(json.get(END_DATE).getAsString(), dateTimeFormatter)
        );
    }

    public String jsonFor(Commitment commitment) {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(COMMITMENT_ID, commitment.id().asString());
        jsonObject.addProperty(CRAFTSPERSON_ID, commitment.craftspersonId().asString());
        jsonObject.addProperty(PROJECT_ID, commitment.projectId().asString());
        jsonObject.addProperty(START_DATE, commitment.startDate().format(dateTimeFormatter));
        jsonObject.addProperty(END_DATE, commitment.endDate().format(dateTimeFormatter));

        return gson.toJson(jsonObject);
    }
}
