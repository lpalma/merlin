package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import com.codurance.merlin.infrastructure.MerlinEnvConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MerlinRepositoryContext {

    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String DATABASE_NAME = "databaseName";
    public static final String CONNECTION_TEST_QUERY = "SELECT 1";

    public static PostgreSQLCommitmentRepository getCommitmentRepository() {
        LightAccess lightAccess = new LightAccess(buildDataSource());

        return new PostgreSQLCommitmentRepository(lightAccess);
    }

    private static HikariDataSource buildDataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.addDataSourceProperty(USER, MerlinEnvConfig.getDatabaseUser());
        hikariConfig.addDataSourceProperty(PASSWORD, MerlinEnvConfig.getDatabasePassword());
        hikariConfig.addDataSourceProperty(DATABASE_NAME, MerlinEnvConfig.getDatabaseName());
        hikariConfig.setJdbcUrl(MerlinEnvConfig.getDatabaseURL());
        hikariConfig.setSchema(MerlinEnvConfig.getDatabaseSchema());
        hikariConfig.setConnectionTestQuery(CONNECTION_TEST_QUERY);

        return new HikariDataSource(hikariConfig);
    }
}
