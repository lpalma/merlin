package com.codurance.merlin.infrastructure;

import com.codurance.merlin.commitment.Commitment;
import com.google.gson.Gson;
import spark.ResponseTransformer;

import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonTransformer implements ResponseTransformer {

    public static final String COMMITMENT_ID = "id";
    public static final String CRAFTSPERSON_ID = "craftspersonId";
    public static final String PROJECT_ID = "projectId";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        if (model instanceof Commitment) {
            return renderCommitment((Commitment) model);
        }

        return gson.toJson(model);
    }

    private String renderCommitment(Commitment commitment) {
        return gson.toJson(Stream.of(
            new SimpleEntry<>(COMMITMENT_ID, commitment.id().asString()),
            new SimpleEntry<>(CRAFTSPERSON_ID, commitment.craftspersonId().asString()),
            new SimpleEntry<>(PROJECT_ID, commitment.projectId().asString()),
            new SimpleEntry<>(START_DATE, commitment.startDate().toString()),
            new SimpleEntry<>(END_DATE, commitment.endDate().toString())
        ).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }
}
