package com.codurance.merlin.service;

import com.codurance.merlin.commitment.Commitment;
import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CommitmentId;
import com.codurance.merlin.commitment.CommitmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommitmentServiceShould {

    @Mock
    private CommitmentRepository repository;

    @Mock
    private Commitment aCommitment;

    @Mock
    private CommitmentData aCommitmentData;

    private CommitmentService service;

    public static final CommitmentId COMMITMENT_ID = new CommitmentId("commitmentId");

    @Before
    public void setUp() {
        service = new CommitmentService(repository);
    }

    @Test
    public void return_all_commitments() {
        when(repository.all()).thenReturn(singletonList(aCommitment));

        assertThat(service.all()).isEqualTo(singletonList(aCommitment));
    }

    @Test
    public void add_new_commitment() {
        when(repository.add(aCommitmentData)).thenReturn(aCommitment);

        assertThat(service.add(aCommitmentData)).isEqualTo(aCommitment);
    }

    @Test
    public void delete_a_commitment() {
        service.delete(COMMITMENT_ID);

        verify(repository).delete(COMMITMENT_ID);
    }
}