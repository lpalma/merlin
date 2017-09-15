package com.codurance.merlin.infrastructure;

public class MerlinEnvConfig {

    public static final String DATABASE_USER = "DATABASE_USER";
    public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";
    public static final String DATABASE_NAME = "DATABASE_NAME";
    private static final String DATABASE_URL = "DATABASE_URL";

    private static final class MerlinEnvConfigHolder {
        private static final MerlinEnvConfig INSTANCE = new MerlinEnvConfig();
    }

    private static final MerlinEnvConfig instance() {
        return MerlinEnvConfigHolder.INSTANCE;
    }

    public String get(String property) {
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
        return instance().get(DATABASE_NAME);
    }

    public static String getDatabaseURL() {
        return instance().get(DATABASE_URL);
    }
}