package com.codurance.merlin.commitment;

import java.util.List;

public interface CommitmentRepository {
    List<Commitment> all();

    Commitment add(CommitmentData commitmentData);

    void deleteAll();
}
