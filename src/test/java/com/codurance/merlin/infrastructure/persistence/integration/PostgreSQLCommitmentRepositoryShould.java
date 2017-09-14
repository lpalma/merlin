package com.codurance.merlin.infrastructure.persistence.integration;

import com.codurance.merlin.commitment.Commitment;
import com.codurance.merlin.craftsperson.Craftsperson;
import com.codurance.merlin.infrastructure.persistence.MerlinRepositoryContext;
import com.codurance.merlin.infrastructure.persistence.PostgreSQLCommitmentRepository;
import com.codurance.merlin.project.Project;
import com.codurance.merlin.valueObject.Id;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostgreSQLCommitmentRepositoryShould {
    public static final Commitment COMMITMENT = new Commitment(
            new Id(3),
            new Craftsperson(new Id(4), "Floki"),
            new Project(new Id(4), "Gama"),
            LocalDate.of(2017, 9, 1),
            LocalDate.of(2017, 12, 22)
    );

    private PostgreSQLCommitmentRepository repository;

    @Before
    public void setUp() {
        repository = MerlinRepositoryContext.getCommitmentRepository();
    }

    @Test
    public void retrieveAllCommitments() throws Exception {
        List<Commitment> commitments = repository.all();

        assertThat(commitments).hasSize(4)
                .contains(COMMITMENT);
    }

}