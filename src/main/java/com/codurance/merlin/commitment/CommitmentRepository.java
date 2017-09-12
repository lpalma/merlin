package com.codurance.merlin.commitment;

import com.codurance.merlin.entity.Craftsperson;
import com.codurance.merlin.entity.Project;
import com.codurance.merlin.valueObjects.Id;
import com.codurance.merlin.valueObjects.Role;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

public class CommitmentRepository {

    public List<Commitment> all() {
        return fakeCommitments();
    }

    private List<Commitment> fakeCommitments() {
        return asList(
                new Commitment(
                    new Id(1),
                    new Craftsperson(new Id(1), "Rollo", new Role("craftsperson")),
                    new Project(new Id(1), "Alpha"),
                    LocalDate.of(2017, 9, 1),
                    LocalDate.of(2017, 12, 22)
                ),
                new Commitment(
                    new Id(2),
                    new Craftsperson(new Id(2), "Lagertha", new Role("craftsperson")),
                    new Project(new Id(1), "Alpha"),
                    LocalDate.of(2017, 9, 1),
                    LocalDate.of(2017, 12, 22)
                ),
                new Commitment(
                    new Id(3),
                    new Craftsperson(new Id(1), "Rollo", new Role("craftsperson")),
                    new Project(new Id(1), "Alpha"),
                    LocalDate.of(2017, 9, 1),
                    LocalDate.of(2017, 12, 22)
                ),
                new Commitment(
                    new Id(4),
                    new Craftsperson(new Id(3), "Bjorn", new Role("craftsperson")),
                    new Project(new Id(2), "Beta"),
                    LocalDate.of(2017, 9, 1),
                    LocalDate.of(2017, 10, 22)
                )
        );
    }
}
