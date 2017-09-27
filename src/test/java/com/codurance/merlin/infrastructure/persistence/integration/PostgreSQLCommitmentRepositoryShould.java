package com.codurance.merlin.infrastructure.persistence.integration;

import com.codurance.merlin.commitment.*;
import com.codurance.merlin.infrastructure.persistence.MerlinRepositoryContext;
import com.codurance.merlin.infrastructure.persistence.PostgreSQLCommitmentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostgreSQLCommitmentRepositoryShould {
    public static final String CRAFTSPERSON_ID = "craftsperson1";
    public static final String PROJECT_ID = "project1";
    public static final String START_DATE = "2017-10-10";
    public static final String END_DATE = "2017-12-10";

    private PostgreSQLCommitmentRepository repository;

    @Before
    public void setUp() {
        repository = MerlinRepositoryContext.getCommitmentRepository();

        repository.deleteAll();
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void add_commitment() {
        CommitmentData aCommitmentData = createCommitmentData();

        Commitment commitment = repository.add(aCommitmentData);

        List<Commitment> commitments = repository.all();

        assertThat(commitments).hasSize(1);
        assertThat(aCommitmentData.equalTo(commitment)).isTrue();
    }

    private CommitmentData createCommitmentData() {
        return new CommitmentData(
                new CraftspersonId(CRAFTSPERSON_ID),
                new ProjectId(PROJECT_ID),
                LocalDate.parse(START_DATE),
                LocalDate.parse(END_DATE)
        );
    }
}