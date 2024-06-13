package com.gastonalt.diariodepesas.config;

public class DatabaseConfig {
    private static final String JDBC_URL = "jdbc:mysql://localhost:2701/diariodepesas?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    public static String getJdbcUrl() {
        return JDBC_URL;
    }

    public static String getJdbcUsername() {
        return JDBC_USERNAME;
    }

    public static String getJdbcPassword() {
        return JDBC_PASSWORD;
    }
}
