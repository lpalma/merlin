package com.codurance.merlin.commitment;

import com.codurance.merlin.infrastructure.commitment.CommitmentJson;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CommitmentShould {

    public static final String COMMITMENT_ID = "commitmentId";
    public static final String CRAFTSPERSON_ID = "craftsperson1";
    public static final String PROJECT_ID = "project1";
    public static final String START_DATE = "2017-10-10";
    public static final String END_DATE = "2017-12-10";

    @Test
    public void represent_own_data_as_json() {
        Commitment commitment = aCommitment();

        CommitmentJson commitmentJson = CommitmentJson.fromCommitment(commitment);

        assertThat(commitment.asJson()).isEqualTo(commitmentJson);
    }

    private Commitment aCommitment() {
        return new Commitment(
            new CommitmentId(COMMITMENT_ID),
            new CraftspersonId(CRAFTSPERSON_ID),
            new ProjectId(PROJECT_ID),
            LocalDate.parse(START_DATE),
            LocalDate.parse(END_DATE)
        );
    }
}