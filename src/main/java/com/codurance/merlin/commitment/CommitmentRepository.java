package com.codurance.merlin.commitment;

import java.util.List;

public interface CommitmentRepository {
    List<Commitment> all();

    void add(Commitment commitment);

    void deleteAll();

    void delete(CommitmentId commitmentId);
}
