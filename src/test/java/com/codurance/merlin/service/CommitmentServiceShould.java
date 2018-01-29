package com.codurance.merlin.service;

import com.codurance.merlin.commitment.*;
import com.codurance.merlin.infrastructure.UniqueIDGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommitmentServiceShould {
    private static final String UNIQUE_ID = "uniqueId";

    private static final CommitmentId COMMITMENT_ID = new CommitmentId(UNIQUE_ID);
    public static final CraftspersonId CRAFTSPERSON_ID = new CraftspersonId("1234");
    public static final ProjectId PROJECT_ID = new ProjectId("4321");
    public static final LocalDate START_DATE = LocalDate.of(2018, 1, 1);
    public static final LocalDate END_DATE = LocalDate.of(2018, 1, 31);

    @Mock
    private CommitmentRepository repository;

    @Mock
    private Commitment aCommitment;

    @Mock
    private UniqueIDGenerator uniqueIDGenerator;

    private CommitmentService service;

    @Before
    public void setUp() {
        service = new CommitmentService(repository, uniqueIDGenerator);
    }

    @Test
    public void return_all_commitments() {
        when(repository.all()).thenReturn(singletonList(aCommitment));

        assertThat(service.all()).isEqualTo(singletonList(aCommitment));
    }

    @Test
    public void add_new_commitment() {
        CommitmentData aCommitmentData = new CommitmentData(CRAFTSPERSON_ID, PROJECT_ID, START_DATE, END_DATE);
        Commitment commitment = new Commitment(COMMITMENT_ID, CRAFTSPERSON_ID, PROJECT_ID, START_DATE, END_DATE );

        when(uniqueIDGenerator.nextId()).thenReturn(UNIQUE_ID);

        Commitment commitmentResult = service.add(aCommitmentData);

        verify(repository).add(commitment);
        assertThat(commitmentResult).isEqualTo(commitment);
    }

    @Test
    public void delete_a_commitment() {
        service.delete(COMMITMENT_ID);

        verify(repository).delete(COMMITMENT_ID);
    }
}