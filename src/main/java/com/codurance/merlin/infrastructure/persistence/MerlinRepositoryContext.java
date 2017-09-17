package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import com.codurance.merlin.infrastructure.MerlinEnvConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MerlinRepositoryContext {

    public static final String CONNECTION_TEST_QUERY = "SELECT 1";

    public static PostgreSQLCommitmentRepository getCommitmentRepository() {
        LightAccess lightAccess = new LightAccess(buildDataSource());

        return new PostgreSQLCommitmentRepository(lightAccess);
    }

    private static HikariDataSource buildDataSource() {
        HikariDataSource ds = new HikariDataSource();

        ds.setJdbcUrl(MerlinEnvConfig.getDatabaseURL());
        ds.setUsername(MerlinEnvConfig.getDatabaseUser());
        ds.setPassword(MerlinEnvConfig.getDatabasePassword());
        ds.setSchema(MerlinEnvConfig.getDatabaseSchema());
        ds.setConnectionTestQuery(CONNECTION_TEST_QUERY);

        return ds;
    }
}
