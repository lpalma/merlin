package com.codurance.merlin.infrastructure;

import com.codurance.merlin.commitment.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CommitmentJsonTransformerShould {
    private static final String COMMITMENT_ID = "commitmentID";
    private static final String CRAFTSPERSON_ID = "craftsperson1";
    private static final String PROJECT_ID = "project1";
    private static final String START_DATE = "2017-10-10";
    private static final String END_DATE = "2017-12-10";

    private static final CommitmentData COMMITMENT_DATA = new CommitmentData(
            null,
            new CraftspersonId(CRAFTSPERSON_ID),
            new ProjectId(PROJECT_ID),
            LocalDate.parse(START_DATE),
            LocalDate.parse(END_DATE)
    );

    private String COMMITMENT_DATA_JSON = "{" +
            "\"craftspersonId\": \"" + CRAFTSPERSON_ID + "\"," +
            "\"projectId\": \"" + PROJECT_ID + "\"," +
            "\"startDate\": \"" + START_DATE + "\"," +
            "\"endDate\": \"" + END_DATE + "\"" +
            "}";

    private String COMMITMENT_JSON = "{" +
            "\"id\":\"" + COMMITMENT_ID + "\"," +
            "\"craftspersonId\":\"" + CRAFTSPERSON_ID + "\"," +
            "\"projectId\":\"" + PROJECT_ID + "\"," +
            "\"startDate\":\"" + START_DATE + "\"," +
            "\"endDate\":\"" + END_DATE + "\"" +
            "}";

    private CommitmentJsonTransformer transformer;

    @Before
    public void setUp() {
        transformer = new CommitmentJsonTransformer();
    }

    @Test
    public void transform_json_to_commitment_data() throws Exception {
        assertThat(transformer.fromJson(COMMITMENT_DATA_JSON)).isEqualTo(COMMITMENT_DATA);
    }

    @Test
    public void transform_commitment_to_json() {
        Commitment commitment = new Commitment(
            new CommitmentId(COMMITMENT_ID),
            COMMITMENT_DATA.craftspersonId(),
            COMMITMENT_DATA.projectId(),
            COMMITMENT_DATA.startDate(),
            COMMITMENT_DATA.endDate()
        );

        assertThat(transformer.jsonFor(commitment)).isEqualTo(COMMITMENT_JSON);
    }
}