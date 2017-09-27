package com.codurance.merlin.infrastructure;

import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CraftspersonId;
import com.codurance.merlin.commitment.ProjectId;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CommitmentDataTransformerShould {
    public static final String CRAFTSPERSON_ID = "craftsperson1";
    public static final String PROJECT_ID = "project1";
    public static final String START_DATE = "2017-10-10";
    public static final String END_DATE = "2017-12-10";

    private CommitmentDataTransformer transformer;

    @Before
    public void setUp() {
        transformer = new CommitmentDataTransformer();
    }

    @Test
    public void transformJsonToCommitmentData() throws Exception {
        assertThat(transformer.fromJson(aCommitmentJson()))
                .isEqualTo(aCommitmentData());
    }

    protected CommitmentData aCommitmentData() {
        return new CommitmentData(
                new CraftspersonId(CRAFTSPERSON_ID),
                new ProjectId(PROJECT_ID),
                LocalDate.parse(START_DATE),
                LocalDate.parse(END_DATE)
        );
    }

    private String aCommitmentJson() {
        return "{" +
                "\"craftspersonId\": \"" + CRAFTSPERSON_ID + "\"," +
                "\"projectId\": \"" + PROJECT_ID + "\"," +
                "\"startDate\": \"" + START_DATE + "\"," +
                "\"endDate\": \"" + END_DATE + "\"" +
                "}";
    }
}