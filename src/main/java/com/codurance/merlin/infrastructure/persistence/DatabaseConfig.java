package com.codurance.merlin.infrastructure.persistence;

import com.codurance.merlin.infrastructure.MerlinEnvConfig;
import org.flywaydb.core.Flyway;

public class DatabaseConfig {

    public static void migrate() {
        Flyway flyway = new Flyway();

        flyway.setDataSource(
                MerlinEnvConfig.getDatabaseURL(),
                MerlinEnvConfig.getDatabaseUser(),
                MerlinEnvConfig.getDatabasePassword()
        );

        flyway.setSchemas(MerlinEnvConfig.getDatabaseSchema());
        flyway.migrate();
    }
}
