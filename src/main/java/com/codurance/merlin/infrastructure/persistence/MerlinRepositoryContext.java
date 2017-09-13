package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import org.postgresql.jdbc2.optional.PoolingDataSource;

public class MerlinRepositoryContext {
    public static PostgreSQLCommitmentRepository getCommitmentRepository() {
        PoolingDataSource ds = new PoolingDataSource();
        ds.setUser("merlin");
        ds.setPassword("merlin@local!");
        ds.setDatabaseName("merlin_development");

        return new PostgreSQLCommitmentRepository(new LightAccess(ds));
    }
}
