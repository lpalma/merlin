package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import com.codurance.lightaccess.mapping.LAResultSet;
import com.codurance.merlin.commitment.*;

import java.util.List;
import java.util.function.Function;

public class PostgreSQLCommitmentRepository implements CommitmentRepository {

    public static final String SELECT_ALL_COMMITMENTS = "SELECT * FROM commitments";

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
                new CraftspersonId(laResultSet.getString(2)),
                new ProjectId(laResultSet.getString(3)),
                laResultSet.getLocalDate(4),
                laResultSet.getLocalDate(5)
        );
    }
}
