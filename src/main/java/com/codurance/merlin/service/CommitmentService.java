package com.codurance.merlin.service;

import com.codurance.merlin.commitment.Commitment;
import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CommitmentId;
import com.codurance.merlin.commitment.CommitmentRepository;

import java.util.List;

public class CommitmentService {
    private CommitmentRepository repository;

    public CommitmentService(CommitmentRepository repository) {
        this.repository = repository;
    }

    public Commitment add(CommitmentData commitmentData) {
        return repository.add(commitmentData);
    }

    public List<Commitment> all() {
        return repository.all();
    }

    public void delete(CommitmentId commitmentId) {
        repository.delete(commitmentId);
    }
}
