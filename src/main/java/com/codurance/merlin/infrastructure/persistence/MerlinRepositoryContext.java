package com.codurance.merlin.infrastructure.persistence;

import com.codurance.lightaccess.LightAccess;
import com.codurance.merlin.infrastructure.MerlinEnvConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class MerlinRepositoryContext {

    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String DATABASE_NAME = "databaseName";
    public static final String DATA_SOURCE_CLASS_NAME = PGSimpleDataSource.class.getName();

    public static PostgreSQLCommitmentRepository getCommitmentRepository() {
        LightAccess lightAccess = new LightAccess(buildDataSource());

        return new PostgreSQLCommitmentRepository(lightAccess);
    }

    private static HikariDataSource buildDataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.addDataSourceProperty(USER, MerlinEnvConfig.getDatabaseUser());
        hikariConfig.addDataSourceProperty(PASSWORD, MerlinEnvConfig.getDatabasePassword());
        hikariConfig.addDataSourceProperty(DATABASE_NAME, MerlinEnvConfig.getDatabaseName());
        hikariConfig.setDataSourceClassName(DATA_SOURCE_CLASS_NAME);

        return new HikariDataSource(hikariConfig);
    }
}
