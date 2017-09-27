package com.codurance.merlin.service;

import com.codurance.merlin.commitment.Commitment;
import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CommitmentRepository;
import com.codurance.merlin.infrastructure.commitment.CommitmentJson;

import java.util.List;
import java.util.stream.Collectors;

public class CommitmentService {
    private CommitmentRepository repository;

    public CommitmentService(CommitmentRepository repository) {
        this.repository = repository;
    }

    public CommitmentJson add(CommitmentData commitmentData) {
        return repository.add(commitmentData).asJson();
    }

    public List<CommitmentJson> all() {
        return repository.all()
                .stream()
                .map(Commitment::asJson)
                .collect(Collectors.toList());
    }
}
