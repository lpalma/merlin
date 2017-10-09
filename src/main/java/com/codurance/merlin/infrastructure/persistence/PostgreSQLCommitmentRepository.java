package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import com.codurance.lightaccess.mapping.LAResultSet;
import com.codurance.merlin.commitment.*;

import java.util.List;
import java.util.function.Function;

public class PostgreSQLCommitmentRepository implements CommitmentRepository {

    public static final String SELECT_ALL_COMMITMENTS = "SELECT * FROM commitments";
    public static final String DELETE_ALL_COMMITMENTS = "DELETE FROM commitments;";
    public static final String INSERT_INTO_COMMITMENTS = "INSERT INTO commitments VALUES (?, ?, ?, date(?), date(?))";
    public static final String COMMITMENTS_SEQUENCE = "commitments_seq";
    private static final String DELETE_FROM_COMMITMENTS = "DELETE FROM commitments WHERE id=?";

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

    public Commitment add(CommitmentData commitmentData) {
        Commitment commitment = createCommitment(commitmentData);

        lightAccess.executeCommand(connection -> connection
            .prepareStatement(INSERT_INTO_COMMITMENTS)
            .withParam(commitment.id().asString())
            .withParam(commitment.craftspersonId().asString())
            .withParam(commitment.projectId().asString())
            .withParam(commitment.startDate().toString())
            .withParam(commitment.endDate().toString())
            .executeUpdate());

        return commitment;
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

    private Commitment createCommitment(CommitmentData commitmentData) {
        return new Commitment(
                nextId(),
                commitmentData.craftspersonId(),
                commitmentData.projectId(),
                commitmentData.startDate(),
                commitmentData.endDate()
        );
    }

    private CommitmentId nextId() {
        int id = lightAccess.nextId(COMMITMENTS_SEQUENCE);

        return new CommitmentId(String.valueOf(id));
    }
}
