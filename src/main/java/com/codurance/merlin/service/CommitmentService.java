package com.codurance.merlin.service;

import com.codurance.merlin.commitment.*;
import com.codurance.merlin.infrastructure.UniqueIdGenerator;

import java.util.List;

public class CommitmentService {
    private CommitmentRepository repository;
    private UniqueIdGenerator uniqueIdGenerator;

    public CommitmentService(CommitmentRepository repository, UniqueIdGenerator uniqueIdGenerator) {
        this.repository = repository;
        this.uniqueIdGenerator = uniqueIdGenerator;
    }

    public Commitment add(CommitmentData commitmentData) {
        Commitment commitment = createCommitment(commitmentData);

        repository.add(commitment);

        return commitment;
    }

    public List<Commitment> all() {
        return repository.all();
    }

    public void delete(CommitmentId commitmentId) {
        repository.delete(commitmentId);
    }

    private Commitment createCommitment(CommitmentData commitmentData) {
        String id = uniqueIdGenerator.nextId();

        return new Commitment(
            new CommitmentId(id),
            commitmentData.craftspersonId(),
            commitmentData.projectId(),
            commitmentData.startDate(),
            commitmentData.endDate()
        );
    }
}
