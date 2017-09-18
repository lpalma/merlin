package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import com.codurance.merlin.infrastructure.MerlinEnvConfig;
import org.postgresql.ds.PGSimpleDataSource;

public class MerlinRepositoryContext {

    public static PostgreSQLCommitmentRepository getCommitmentRepository() {
        LightAccess lightAccess = new LightAccess(buildDataSource());

        return new PostgreSQLCommitmentRepository(lightAccess);
    }

    private static PGSimpleDataSource buildDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();

        ds.setDatabaseName(MerlinEnvConfig.getDatabaseName());
        ds.setServerName(MerlinEnvConfig.getDatabaseHost());
        ds.setPortNumber(MerlinEnvConfig.getDatabasePortNumber());
        ds.setUser(MerlinEnvConfig.getDatabaseUser());
        ds.setPassword(MerlinEnvConfig.getDatabasePassword());

        return ds;
    }
}
