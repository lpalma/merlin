package com.codurance.merlin.service;

import com.codurance.merlin.commitment.Commitment;
import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CommitmentId;
import com.codurance.merlin.commitment.CommitmentRepository;
import com.codurance.merlin.infrastructure.commitment.CommitmentJson;

import java.util.List;
import java.util.stream.Collectors;

public class CommitmentService {
    private CommitmentRepository repository;

    public CommitmentService(CommitmentRepository repository) {
        this.repository = repository;
    }

    public Commitment add(CommitmentData commitmentData) {
        return repository.add(commitmentData);
    }

    public List<CommitmentJson> all() {
        return repository.all()
                .stream()
                .map(Commitment::asJson)
                .collect(Collectors.toList());
    }

    public void delete(CommitmentId commitmentId) {
        repository.delete(commitmentId);
    }
}
