package com.insurance.fileservice.testhelper;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainerSetupHelper {
    private static PostgreSQLContainer<?> postgresContainer;

    public static void startContainer() {
        if (postgresContainer == null) {
            postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                    .withDatabaseName("file_db")
                    .withUsername("postgres")
                    .withPassword("138643576");
            postgresContainer.start();
        }
    }

    public static void stopContainer() {
        if (postgresContainer != null) {
            postgresContainer.stop();
        }
    }

    public static String getPostgresJdbcUrl() {
        return postgresContainer.getJdbcUrl();
    }

    public static String getPostgresUsername() {
        return postgresContainer.getUsername();
    }

    public static String getPostgresPassword() {
        return postgresContainer.getPassword();
    }
}
