package com.codurance.merlin.infrastructure;

public class MerlinEnvConfig {

    public static final String DATABASE_USER = "DATABASE_USER";
    public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";
    public static final String DEFAULT_DATABASE_NAME = "merlin";
    private static final String DATABASE_URL = "DATABASE_URL";
    public static final String DEFAULT_DATABASE_SCHEMA = "merlin";
    public static final String DATABASE_PORT = "DATABASE_PORT";
    public static final String DATABASE_HOST = "DATABASE_HOST";

    private static final class MerlinEnvConfigHolder {
        private static final MerlinEnvConfig INSTANCE = new MerlinEnvConfig();
    }

    private static final MerlinEnvConfig instance() {
        return MerlinEnvConfigHolder.INSTANCE;
    }

    private String get(String property) {
        String value =  System.getenv(property);

        if (value == null) {
            throw new EnvironmentConfigurationException("variable " + property + " is not set.");
        }

        return value;
    }

    public static String getDatabaseUser() {
        return instance().get(DATABASE_USER);
    }

    public static String getDatabasePassword() {
        return instance().get(DATABASE_PASSWORD);
    }

    public static String getDatabaseName() {
        return DEFAULT_DATABASE_NAME;
    }

    public static String getDatabaseURL() {
        return instance().get(DATABASE_URL);
    }

    public static String getDatabaseSchema() {
        return DEFAULT_DATABASE_SCHEMA;
    }

    public static String getDatabaseHost() {
        return instance().get(DATABASE_HOST);
    }

    public static int getDatabasePortNumber() {
        return Integer.valueOf(instance().get(DATABASE_PORT));
    }
}