package com.codurance.merlin.service;

import com.codurance.merlin.commitment.*;
import com.codurance.merlin.infrastructure.UniqueIdGenerator;
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

    @Mock
    private CommitmentRepository repository;

    @Mock
    private Commitment aCommitment;

    @Mock
    private UniqueIdGenerator uniqueIdGenerator;

    private CommitmentService service;

    @Before
    public void setUp() {
        service = new CommitmentService(repository, uniqueIdGenerator);
    }

    @Test
    public void return_all_commitments() {
        when(repository.all()).thenReturn(singletonList(aCommitment));

        assertThat(service.all()).isEqualTo(singletonList(aCommitment));
    }

    @Test
    public void add_new_commitment() {
        CraftspersonId craftspersonId = new CraftspersonId("1234");
        ProjectId projectId = new ProjectId("4321");
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2018, 1, 31);

        CommitmentData aCommitmentData = new CommitmentData(craftspersonId, projectId, startDate, endDate);

        Commitment commitment = new Commitment(
            new CommitmentId(UNIQUE_ID),
            craftspersonId,
            projectId,
            startDate,
            endDate
        );

        when(uniqueIdGenerator.nextId()).thenReturn(UNIQUE_ID);

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