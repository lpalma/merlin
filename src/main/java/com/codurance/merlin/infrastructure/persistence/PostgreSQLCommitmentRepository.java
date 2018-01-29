package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import com.codurance.lightaccess.mapping.LAResultSet;
import com.codurance.merlin.commitment.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class PostgreSQLCommitmentRepository implements CommitmentRepository {

    public static final String SELECT_ALL_COMMITMENTS = "SELECT * FROM commitments";
    public static final String DELETE_ALL_COMMITMENTS = "DELETE FROM commitments;";
    public static final String INSERT_INTO_COMMITMENTS = "INSERT INTO commitments VALUES (?, ?, ?, date(?), date(?))";
    private static final String DELETE_FROM_COMMITMENTS = "DELETE FROM commitments WHERE id=?";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

    private LightAccess lightAccess;

    public PostgreSQLCommitmentRepository(LightAccess lightAccess) {
        this.lightAccess = lightAccess;
    }

    @Override
    public List<Commitment> all() {
        return lightAccess.executeQuery(connection -> connection
                .prepareStatement(SELECT_ALL_COMMITMENTS)
                .executeQuery()
                .mapResults(toCommitment()));
    }

    public void add(Commitment commitment) {
        lightAccess.executeCommand(connection -> connection
            .prepareStatement(INSERT_INTO_COMMITMENTS)
            .withParam(commitment.id().asString())
            .withParam(commitment.craftspersonId().asString())
            .withParam(commitment.projectId().asString())
            .withParam(commitment.startDate().format(dateTimeFormatter))
            .withParam(commitment.endDate().format(dateTimeFormatter))
            .executeUpdate());
    }

    public void deleteAll() {
        lightAccess.executeCommand(connection -> connection
                .prepareStatement(DELETE_ALL_COMMITMENTS)
                .executeUpdate());
    }

    public void delete(CommitmentId commitmentId) {
        lightAccess.executeCommand(connection -> connection
                .prepareStatement(DELETE_FROM_COMMITMENTS)
                .withParam(commitmentId.asString())
                .executeUpdate());
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
