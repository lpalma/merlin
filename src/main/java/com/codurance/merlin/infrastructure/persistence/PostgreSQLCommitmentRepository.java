package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import com.codurance.lightaccess.mapping.LAResultSet;
import com.codurance.merlin.commitment.Commitment;
import com.codurance.merlin.commitment.CommitmentId;
import com.codurance.merlin.commitment.CommitmentRepository;
import com.codurance.merlin.craftsperson.Craftsperson;
import com.codurance.merlin.craftsperson.CraftspersonId;
import com.codurance.merlin.project.Project;
import com.codurance.merlin.project.ProjectId;

import java.util.List;
import java.util.function.Function;

public class PostgreSQLCommitmentRepository implements CommitmentRepository {

    public static final String SELECT_ALL_COMMITMENTS = "SELECT c.id, craft.id, craft.name, p.id, p.name, c.start_date, c.end_date " +
            "FROM commitments AS c " +
            "LEFT JOIN craftspeople AS craft ON c.craftsperson_id = craft.id " +
            "LEFT JOIN projects AS p ON c.project_id = p.id";

    private LightAccess lightAccess;

    public PostgreSQLCommitmentRepository(LightAccess lightAccess) {
        this.lightAccess = lightAccess;
    }

    @Override
    public List<Commitment> all() {
        return lightAccess.executeQuery(connection -> connection.
                prepareStatement(SELECT_ALL_COMMITMENTS)
                .executeQuery()
                .mapResults(toCommitment()));
    }

    private Function<LAResultSet, Commitment> toCommitment() {
        return laResultSet -> new Commitment(
                new CommitmentId(laResultSet.getString(1)),
                new Craftsperson(new CraftspersonId(laResultSet.getString(2)), laResultSet.getString(3)),
                new Project(new ProjectId(laResultSet.getString(4)), laResultSet.getString(5)),
                laResultSet.getLocalDate(6),
                laResultSet.getLocalDate(7)
        );
    }
}
