package com.codurance.merlin.commitment;

import com.codurance.merlin.infrastructure.UniqueIDGenerator;

import java.util.List;

public class CommitmentService {
    private CommitmentRepository repository;
    private UniqueIDGenerator uniqueIDGenerator;

    public CommitmentService(CommitmentRepository repository, UniqueIDGenerator uniqueIDGenerator) {
        this.repository = repository;
        this.uniqueIDGenerator = uniqueIDGenerator;
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
        String id = uniqueIDGenerator.nextId();

        return new Commitment(
            new CommitmentId(id),
            commitmentData.craftspersonId(),
            commitmentData.projectId(),
            commitmentData.startDate(),
            commitmentData.endDate()
        );
    }
}
